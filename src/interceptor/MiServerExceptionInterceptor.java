package interceptor;

import util.WrappedRuntimeException;
import webapps.WebappsConstants;
import bl.exceptions.MiServerException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * this Interceptor mainly focus on business exception that is inherited with {@link bl.exceptions.MiServerException}
 * 
 * @author peter
 * 
 */
public class MiServerExceptionInterceptor extends AbstractInterceptor {
    protected static Logger LOG = LoggerFactory.getLogger(MiServerExceptionInterceptor.class);
    private static final long serialVersionUID = 6781679050585317814L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String result;
        try {
            result = invocation.invoke();
        } catch (MiServerException e) {
            if (invocation.getAction() instanceof ActionSupport) {
                ActionSupport as = (ActionSupport) invocation.getAction();
                String errorMessage = MessageFormat.format(e.getKeyMessage(), e.getParameterMessage());
                LOG.error(errorMessage);
                as.addActionError(errorMessage);
                return as.INPUT;
            } else {
                LOG.error("This action exception is: {}", e);
                throw new WrappedRuntimeException(e);
            }
        }
        return result;
    }

}
