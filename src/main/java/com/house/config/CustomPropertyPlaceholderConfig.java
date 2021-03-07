package com.house.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 将 properties 文件的属性注入到类的静态常量中
 */
public class CustomPropertyPlaceholderConfig extends PropertyPlaceholderConfigurer {

    /**
     * 需要注入的常量类名数组
     */
    private String[] configureClasses;

    /**
     * 是否区分大小写，默认否
     */
    private boolean lowerCaseKey = false;

    @Override
    @SuppressWarnings("unchecked")
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);

        if (configureClasses == null) {
            return;
        }

        Map<String, String> propMap = new HashMap<>(props.size());
        String key = null;
        // 1.取出 properties 文件中的kv，并存储到 propMap 中
        if (lowerCaseKey) {
            // key不区分大小写，所以将所有key转换成小写
            for (Enumeration<?> k = props.propertyNames(); k.hasMoreElements(); ) {
                key = (String) k.nextElement();
                propMap.put(key.toLowerCase(), props.getProperty(key));
            }
        } else {
            // key区分大小写
            for (Enumeration<?> k = props.propertyNames(); k.hasMoreElements(); ) {
                key = (String) k.nextElement();
                propMap.put(key, props.getProperty(key));
            }
        }

        Class c = null;
        Field[] fields = null;
        String keyStr = null, value = null;
        // 2.遍历多个常量类
        for (int i = 0; i < configureClasses.length; i++) {
            try {
                c = Class.forName(configureClasses[i]);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                continue;
            }

            // 3.获取类中的字段
            fields = c.getFields();

            // 遍历属性列表，注入到类的常量字段中
            for (int j = 0; fields != null && j < fields.length; j++) {
                keyStr = fields[j].getName();
                // 4.根据常量类中的字段，从 propMap 中取出相应的v
                value = propMap.get(lowerCaseKey ? keyStr.toLowerCase() : key);
                if (null != value && value.length() > 0) {
                    // 值必须存在
                    if (value != null) {
                        value = value.trim();   //删除字符串的头尾空白符
                        fields[j].setAccessible(true);
                        try {
                            // 5.判断常量类字段的类型并将 propMap 的v注入到常量类的字段
                            if (Integer.TYPE.equals(fields[j].getType())) {
                                fields[j].setInt(null, Integer.parseInt(value));
                            } else if (Long.TYPE.equals(fields[j].getType())) {
                                fields[j].setLong(null, Long.parseLong(value));
                            } else if (Short.TYPE.equals(fields[j].getType())) {
                                fields[j].setShort(null, Short.parseShort(value));
                            } else if (Double.TYPE.equals(fields[j].getType())) {
                                fields[j].setDouble(null, Double.parseDouble(value));
                            } else if (Float.TYPE.equals(fields[j].getType())) {
                                fields[j].setFloat(null, Float.parseFloat(value));
                            } else if (Boolean.TYPE.equals(fields[j].getType())) {
                                fields[j].setBoolean(null, Boolean.parseBoolean(value));
                            } else {
                                fields[j].set(null, value);
                            }
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } finally {
                            fields[j].setAccessible(false);
                        }
                    }
                }
            }
        }

    }

    public String[] getConfigureClasses() {
        return configureClasses;
    }

    public void setConfigureClasses(String[] configureClasses) {
        this.configureClasses = configureClasses;
    }

    public boolean isLowerCaseKey() {
        return lowerCaseKey;
    }

    public void setLowerCaseKey(boolean lowerCaseKey) {
        this.lowerCaseKey = lowerCaseKey;
    }
}
