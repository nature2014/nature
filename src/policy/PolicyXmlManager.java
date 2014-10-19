package policy;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import policy.schema.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limin.llm on 2014/10/19.
 * 策略模版的管理者，提供系统的Policy.xml的原始信息
 */
public class PolicyXmlManager {
    private static PolicyXmlManager policyXmlManager = new PolicyXmlManager();
    private static Logger LOG = LoggerFactory.getLogger(PolicyXmlManager.class);
    private static volatile boolean load = false;

    private PolicyXmlManager() {
    }

    private Map<String, ConditionEntry> conditionEntryMap = new HashMap<String, ConditionEntry>();
    private Map<String, ActionEntry> actionEntryMap = new HashMap<String, ActionEntry>();

    public static PolicyXmlManager getInstance() {
        if (!load) {
            LOG.info("启动Policy.xml装载");
            synchronized (PolicyXmlManager.class) {
                policyXmlManager.load();
            }
            load = true;
            LOG.info("结束Policy.xml装载");
        }
        return policyXmlManager;
    }

    public Map<String, ConditionEntry> getConditionEntryMap() {
        return conditionEntryMap;
    }

    public Map<String, ActionEntry> getActionEntryMap() {
        return actionEntryMap;
    }

    public ActionEntry queryActionById(String id) {
        return this.actionEntryMap.get(id);
    }

    public ConditionEntry queryConditionById(String id) {
        return this.conditionEntryMap.get(id);
    }

    public synchronized void load() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PolicyType.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            PolicyType policyType = (PolicyType) unmarshaller.unmarshal(
                    new File("D:\\workspace_study\\nature\\srcresources\\policy\\policy.xml"));
            ActionEntries actionEntries = policyType.getActions();
            if (actionEntries != null) {
                List<ActionEntry> actionEntryList = actionEntries.getActionEntry();
                if (actionEntryList != null) {
                    for (int i = 0; i < actionEntryList.size(); i++) {
                        actionEntryMap.put(actionEntryList.get(i).getId(), actionEntryList.get(i));
                    }
                }
            }
            ConditionEntries conditionEntries = policyType.getConditions();
            if (conditionEntries != null) {
                List<ConditionEntry> conditionEntryList = conditionEntries.getConditionEntry();
                if (conditionEntryList != null) {
                    for (int i = 0; i < conditionEntryList.size(); i++) {
                        conditionEntryMap.put(conditionEntryList.get(i).getId(), conditionEntryList.get(i));
                    }
                }
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
            System.out.println(e);
        }
    }

    public void reload() {
        this.load = false;
        load();
    }

    public static void main(String[] args) {
        PolicyXmlManager policyXmlManager = PolicyXmlManager.getInstance();
        ActionEntry actionEntry = policyXmlManager.queryActionById("action_score");
        System.out.println(ToStringBuilder.reflectionToString(actionEntry));
    }
}
