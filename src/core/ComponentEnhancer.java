package core;

import core.componentext.*;
import dynamicschema.*;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by wangronghua on 14-2-11.
 */

public class ComponentEnhancer {

  public static Component wrap(TextEntry text) {
    TextExtension extension = new TextExtension();
    try {
      BeanUtils.copyProperties(extension, text);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      //todo exception
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return extension;
  }
  public static Component wrap(TextAreaEntry textArea) {

    return textArea;
  }
  public static Component wrap(CheckboxEntry checkbox) {
    CheckboxExtension extension = new CheckboxExtension();
    try {
      BeanUtils.copyProperties(extension, checkbox);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    extension.init();
    return extension;
  }
  public static Component wrap(RadioEntry radio) {
    RadioExtension extension = new RadioExtension();
    try {
      BeanUtils.copyProperties(extension, radio);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    extension.init();
    return extension;
  }
  public static Component wrap(ImageEntry image) {

    return image;
  }
  public static Component wrap(DateEntry date) {

    return date;
  }
  public static Component wrap(SelectEntry select) {
    SelectExtension extension = new SelectExtension();
    try {
      BeanUtils.copyProperties(extension, select);

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    extension.init();
    return extension;
  }
  public static Component wrap(Button button) {

    return button;
  }
  public static Component wrap(Section fieldSet) {
    FieldSetExtension extension = new FieldSetExtension();
    try {
      BeanUtils.copyProperties(extension, fieldSet);

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return extension;
  }
  public static Component wrap(Form form) {
    FormExtension extension = new FormExtension();
    try {
      BeanUtils.copyProperties(extension, form);

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return extension;
  }

  public static Component wrap(Row row) {
    RowExtension extension = new RowExtension();
    try {
      BeanUtils.copyProperties(extension, row);

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return extension;
  }

  public static Component wrap(Document document) {
    DocumentExtension extension = new DocumentExtension();
    try {
      BeanUtils.copyProperties(extension, document);

    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return extension;
  }

  public static ComponentBase wrap(ComponentBase component) {
    return component;
  }
}
