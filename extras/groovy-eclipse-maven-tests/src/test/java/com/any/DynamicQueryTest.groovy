package com.any

import junit.framework.TestCase
import groovy.transform.TypeChecked

@TypeChecked
class DynamicQueryTest extends TestCase {
    private DynamicQuery queryCreator = new DynamicQuery()
    String userName = "Byron"
    String afterDate = "1901"
    String beforeDate = "1900"

    Map whereClauseElements = [
            userName: "user = '@userName@'",
            afterDate: "updatedOn > '@afterDate@'",
            beforeDate: "updatedOn < '@beforeDate@'",
            promptName: "ca.configuration.icmInput.description LIKE '%@promptName@%'",
            customerHhrr:
"ca.configuration.datamodeBuildLevel.datamode.customer.hhrr = '@customerHhrr@'",
            datamodeName:
"ca.configuration.datamodeBuildLevel.datamode.datamode = '@datamodeName@'",
            buildLabel:
"ca.configuration.datamodeBuildLevel.buildnumber.label = '@buildLabel@'"
    ]

    public void testNoWhereClause() {
        String query = createQuery([:], null, null)
        assertEquals("SELECT ca FROM ConfigurationAudit as ca", query)
    }

    public void testNoWhereClauseAndSortTypeAndOrderTypeNew(){
        String sortType = "hey"
        String orderType = "there"
        String query =  createQuery([:],  sortType, orderType)
        assertEquals ("SELECT ca FROM ConfigurationAudit as ca ORDER BY hey there", query)
    }

    public void testNoWhereClauseAndSortTypeAndNoOrderTypeNew(){
        String query =  createQuery([:],  "hey", "")
        assertEquals ("SELECT ca FROM ConfigurationAudit as ca ORDER BY hey", query)
    }

    public void testNoWhereClauseAndNoSortTypeAndOrderTypeNew(){
        String query = createQuery([:],  "",  "there")
        assertEquals ("SELECT ca FROM ConfigurationAudit as ca ORDER BY there", query)
    }

    public void testNoWhereClauseAndNoSortTypeAndNoOrderTypeNew(){
        String query = createQuery([:],  "",  "")
        assertEquals ("SELECT ca FROM ConfigurationAudit as ca", query)
    }

    public void testOnlyUserName() {
        String query =  createQuery([userName:userName],  null, null)
        assertEquals ("SELECT ca FROM ConfigurationAudit as ca WHERE user = '${userName}'", query)
    }

    public void testUserNameAndAfterDate() {
        String query =  createQuery([userName:userName,
afterDate:afterDate],  null, null)
        assertEquals("SELECT ca FROM ConfigurationAudit as ca WHERE user = '${userName}' AND updatedOn > '${afterDate}'", query)
    }

    public void testAfterDateAndNoUserName() {
        userName = null
        String query =  createQuery([userName:userName,
afterDate:afterDate],  null, null)
        assertEquals("SELECT ca FROM ConfigurationAudit as ca WHERE updatedOn > '${afterDate}'", query)
    }

    public void testBeforeDateAndAfterDateAndUserName() {
        String query =  createQuery([userName: userName,afterDate:
afterDate, beforeDate: beforeDate], null, null)
        assertEquals("SELECT ca FROM ConfigurationAudit as ca WHERE user = '${userName}' AND updatedOn > '${afterDate}' AND updatedOn < '${beforeDate}'", query)
    }

    public void testBeforeDateAndAfterDateAndNoUserNameNew() {
        String userName = ""
        String query =  createQuery([userName: userName,afterDate:
afterDate, beforeDate: beforeDate], null, null)
        assertEquals("SELECT ca FROM ConfigurationAudit as ca WHERE updatedOn > '${afterDate}' AND updatedOn < '${beforeDate}'", query)
    }

    public void testBeforeDateAndNoAfterDateAndUserName() {
        String afterDate = ""
        String query =  createQuery([userName: userName,afterDate:
afterDate, beforeDate: beforeDate], null, null)
        assertEquals("SELECT ca FROM ConfigurationAudit as ca WHERE user = '${userName}' AND updatedOn < '${beforeDate}'", query)
    }

    public void testBeforeDateAndNoAfterDateAndNoUserName() {
        String userName = ""
        String afterDate = ""
        String query =  createQuery([userName: userName,afterDate:
afterDate, beforeDate: beforeDate], null, null)
        assertEquals("SELECT ca FROM ConfigurationAudit as ca WHERE updatedOn < '${beforeDate}'", query)
    }

    public void testPromptName() {
        String promptName = "thePrompt"
        String query =  createQuery([promptName:promptName], null, null)
        assertEquals("SELECT ca FROM ConfigurationAudit as ca WHERE ca.configuration.icmInput.description LIKE '%thePrompt%'", query)
    }

    public void testCustomerHhrr() {
        String customerHhrr = "aCustomer"
        String query =  createQuery([customerHhrr:customerHhrr], null, null)
        assertEquals("SELECT ca FROM ConfigurationAudit as ca WHERE ca.configuration.datamodeBuildLevel.datamode.customer.hhrr = 'aCustomer'",
query)
    }

    public void testDatamodeName() {
        String datamodeName = "aDatamodeName"
        String query =  createQuery([datamodeName:datamodeName], null, null)
        assertEquals("SELECT ca FROM ConfigurationAudit as ca WHERE ca.configuration.datamodeBuildLevel.datamode.datamode = '${datamodeName}'",
query)
    }
    public void testBuildLabel() {
        String buildLabel = "aBuildLabel"
        String query =  createQuery([buildLabel:buildLabel], null, null)
        assertEquals("SELECT ca FROM ConfigurationAudit as ca WHERE ca.configuration.datamodeBuildLevel.buildnumber.label = '${buildLabel}'",
query)
    }

    public void testInvalidQueryParam() {
        String paramThatDoesNotExistAsKeyInWhereClauseElements = "xx"
        try {
            createQuery([(paramThatDoesNotExistAsKeyInWhereClauseElements):
"whoCares?"], null, null)
            fail()
        } catch (IllegalArgumentException ex) {

assertTrue(ex.message.contains(paramThatDoesNotExistAsKeyInWhereClauseElements))
        }
    }

    private String createQuery(Map queryParams, String sortType, String orderType){
        queryCreator.createQueryNew(queryParams, "SELECT ca FROM ConfigurationAudit as ca", whereClauseElements, [sortType, orderType])
    }
}

