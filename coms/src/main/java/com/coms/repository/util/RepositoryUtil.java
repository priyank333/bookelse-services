/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coms.repository.util;

import java.time.LocalDate;

/**
 *
 * @author z0043uwn
 */
public class RepositoryUtil {


    public static <AttributeType> void setValue(
            AttributeType attributeValue,
            String tableAlias,
            String attribute,
            StringBuilder whereCondition) {
        if (attributeValue != null) {
            whereCondition.append(" and ").
                    append(tableAlias).
                    append(".").
                    append(attribute);
            if (attributeValue.getClass().equals(String.class)) {
                whereCondition.
                        append(" = '").
                        append(attributeValue).append("' ");
            } else {
                whereCondition.
                        append(" = ").
                        append(attributeValue);
            }

        }
    }

    public static void setLike(String attributeValue,
            String tableAlias, String attribute, StringBuilder whereCondition) {
        if (attributeValue != null) {
            whereCondition.append(" and UPPER(").
                    append(tableAlias).
                    append(".").
                    append(attribute).append(") like '%").
                    append(attributeValue.toUpperCase()).
                    append("%'");
        }
    }

    public static void setDateRange(LocalDate fromDate, LocalDate toDate,
            String tableAlias, String attribute, StringBuilder whereCondition) {
        if (fromDate != null && toDate != null) {
            whereCondition.append(" and ").
                    append(tableAlias).
                    append(".").
                    append(attribute).
                    append(" between '").
                    append(fromDate).
                    append("' and '").
                    append(toDate).
                    append("'");
        }
    }
}
