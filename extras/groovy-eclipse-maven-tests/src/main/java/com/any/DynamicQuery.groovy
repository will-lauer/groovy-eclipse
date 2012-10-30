package com.any

import groovy.transform.TypeChecked

@TypeChecked
class DynamicQuery {

    public String createQueryNew(Map queryParams,  String initial, Map whereClauseElements, List orderBys = []) {
        validateNoParamsThatAreNotInWhereClauseElements(queryParams, whereClauseElements)
        String whereClause = makeAndedWhereClause(queryParams, whereClauseElements)
        initial + (whereClause == "" ? "" : " WHERE " + whereClause) + orderBy(orderBys)
    }

    private validateNoParamsThatAreNotInWhereClauseElements(Map queryParams, Map whereClauseElements){
        List invalidKeys = queryParams.findAll { 
            String key, String value ->
            !whereClauseElements.containsKey(key)}.collect{ Map.Entry it ->it.key}
        if(! invalidKeys.isEmpty()){
            throw new IllegalArgumentException("At least one invalid key has been found.  Invalid keys are ${invalidKeys}." + " Keys should be in this set: ${whereClauseElements.keySet()}")
        }
    }

    private String orderBy(List orderBys){
        List cleaned = orderBys.findAll{String elem -> stringNotEmpty(elem)}
        cleaned.isEmpty() ? "" :  " ORDER BY " + cleaned.join(" ")
    }

    public String makeAndedWhereClause(Map queryParams, Map
whereClauseElements) {
        and(makeWhereClauseElements(queryParams, whereClauseElements))
    }

    public String and(List elements){
        elements.join(" AND ")
    }

    public List makeWhereClauseElements(Map queryParams, Map
whereClauseElements) {
        filterOutIMapEntriesWithEmptyValues(queryParams,
whereClauseElements).
                collect {String key, String value ->
resolveAgainstMap(whereClauseElements, key, value)}
    }

    private Map filterOutIMapEntriesWithEmptyValues(Map queryParams, Map
whereClauseElements){
        queryParams.findAll{String key, String value ->
stringNotEmpty(value)}
    }

    private String resolveAgainstMap(Map<String, String>
whereClauseElements, String key, String value) {
        "${whereClauseElements[key].replace("@${key}@",value)}"
    }

    private boolean stringNotEmpty(String str) {
        str != null && str.trim() != ""
    }
}