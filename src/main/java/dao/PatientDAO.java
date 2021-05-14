package dao;

import entity.Patient;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import java.util.stream.Collectors;
public final class PatientDAO {

    private static final String SQL_FRAGMENT_TABLE_NAME = "TableName";
    private static final String DEFAULT_RESULT_MAP = "resultPatients";
    private static SqlSession session;
    private final String mappingName;
    private final String defaultResultMapName;
    private List<ResultMappingWrapper> resultMappings;
    private String tableName;

    public PatientDAO(SqlSession session, String mappingName) {
        this(session, mappingName, DEFAULT_RESULT_MAP);
    }

    public PatientDAO(SqlSession session, String mappingName, String defaultResultMap) {
        this.session = session;
        this.mappingName = mappingName;
        this.defaultResultMapName = defaultResultMap;
        initMappings();
    }


    private void initMappings() {
        Configuration conf = session.getConfiguration();
        ResultMap resultMap = conf.getResultMap(mappingName);
        this.tableName = "PATIENTS";
        this.resultMappings = resultMap.getResultMappings().stream()
                .map(item -> new ResultMappingWrapper(item, conf))
                .collect(Collectors.toList());
    }

    public static List<Patient> GetAllData(){

        return session.selectList("resources.config.PatientMapper.selectAll");
    }

    public List<Patient> GetCustomData(Patient patient){

        return session.selectList("resources.config.PatientMapper.selectCustomData", new DAOWrapper(patient));
    }

    public void SavePatient(Patient patient){
        session.insert("resources.config.PatientMapper.insertPatient", new DAOWrapper(patient));
        session.commit();
    }

    public int updatePatient(Patient patient){
        return session.update("resources.config.PatientMapper.update", patient);
    }

    public void DeletePatient(Patient patient){
        session.delete("resources.config.PatientMapper.delete", new DAOWrapper(patient) );
        session.commit();
    }

    public List<ResultMappingWrapper> getResultMappings() {
        return resultMappings;
    }

    public class DAOWrapper {
        //CRUD entity
        private Object entity;

        private DAOWrapper(Object entity) {
            this.entity = entity;
        }

        public Object getEntity() {
            return entity;
        }

        public String getTableName() {
            return tableName;
        }

        public List<ResultMappingWrapper> getResultMappings() {
            return resultMappings;
        }

    }


    public static class ResultMappingWrapper {
        private ResultMapping source;
        private String nestedProperty;

        private ResultMappingWrapper(ResultMapping source, Configuration conf) {
            this.source = source;
            if (source.getNestedQueryId() != null) {
                MappedStatement statement = conf.getMappedStatement(source.getNestedQueryId());
                ResultMap nestedResultMap = conf.getResultMap(statement.getResultMaps().get(0).getId());
                nestedProperty = source.getProperty() + "." + nestedResultMap.getIdResultMappings().get(0).getProperty();
            }
        }

        public String getProperty() {
            return nestedProperty != null ? nestedProperty : source.getProperty();
        }

        public String getColumn() {
            return source.getColumn();
        }

        public boolean getIsId() {
            return source.getFlags().contains(ResultFlag.ID);
        }

    }

}
