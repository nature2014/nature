package wechat.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DBUtils;
import wechat.BaseMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangronghua on 14-3-19.
 */
public class MessageBus {

  protected final static Logger LOG = LoggerFactory.getLogger(MessageBus.class);

  private static BlockingQueue<BaseMessage> queue = new ArrayBlockingQueue<BaseMessage>(100);
  private static ExecutorService executor = Executors.newFixedThreadPool(10);
  private static List<EventHandler> eventHandlers = new ArrayList<EventHandler>();
  private static MessageBus instance = new MessageBus();
  private static Thread dispactherThread;


  public static MessageBus get() {
    return instance;
  }

  private MessageBus () {
    ThreadDispacther dispacther = new ThreadDispacther();
    executor.submit(dispacther);
//    Thread thread = new Thread(dispacther);
//    thread.setDaemon(true);
//    thread.start();
  }

  public static void destroy() {
    if(null != executor) {
      executor.shutdownNow();
    }
  }

  public boolean add(BaseMessage message) {
    message.setDbFlag(DBUtils.getDBFlag());
    return queue.offer(message);
  }

  public void addHandler(EventHandler handler) {
    eventHandlers.add(handler);
  }

  class ThreadDispacther implements Runnable {
    @Override
    public void run() {
      while(!Thread.interrupted()) {
        try {
          BaseMessage message = queue.take();
          TaskRunner runner = new TaskRunner(eventHandlers, message);
          executor.submit(runner);
        } catch (InterruptedException e) {
          LOG.error("Core Messgae handle Thread down:", e);
          executor.shutdownNow();
          break;
        }
      }

      if(Thread.interrupted()) {
        throw new RuntimeException("Thread[MessageBusMain] is interruptted.");
      }
    }
  }

  class TaskRunner implements Runnable {
    private List<EventHandler> handlers;
    private BaseMessage message;
    TaskRunner (List<EventHandler> ehandlers, BaseMessage message) {
      this.handlers = ehandlers;
      this.message = message;
    }

    @Override
    public void run() {
      for(EventHandler handler : handlers) {
        if(handler.acceptEvent(message)) {
          handler.handle(message);
        }

      }
    }
  }

}
