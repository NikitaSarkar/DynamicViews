# DynamicViews

This app creates dynamic views using JSON Array.

There are two screens in the app. The 1st screen has an Add Button and a Total Reports count.
Total reports will be zero initially as you haven't added any reports.


When you click on the Add button, the second screen is shown.
The second screen is completely dynamic. You have to keep a JSON Array to create this screen.


For example consider the following JSON:
[
{'field-name':'name', 'type':'text', 'required':true}, {'field-name':'age', 'type':'number', 'min':18, 'max':65}, {'field-name':'address', 'type':'multiline'}
]
   
 
 
 If input JSON is as below:
[
{'field-name':'name', 'type':'text'},
{'field-name':'age', 'type':'number'},
{'field-name':'gender', 'type':dropdown', 'options':['male', 'female', 'other']}, {'field-name':'address', 'type':'multiline'}
]

The drop-down field can be displayed as a spinner.
Suppose for the second screen, you entered the name as 'Hari', age: 29, gender: 'male' and Address: 'Oxford Engg College, Bangalore, Karnataka'


After entering this data, if you tap the 'Done' button, you should prepare a JSON in the following format:
{'Name':'Hari', 'Age':29, 'Gender':'male', 'Address':'Oxford Engg College, Bangalore, Karnataka' }
You may print the above data as a log statement


Once you fill in data and click the Done button, control goes back to the 1st page. and it will look like shown below:
 The listing page should display 1st two fields' data. If you have only 1 field, display just that.


About Validations:
required, min, max etc. work as expected; that is if a field has 'required': true, and if you leave it unfilled, upon tapping the done button, an error msg/toast shall be displayed. For min, max the value entered should be within the range. Else as before an error toast/msg will be displayed upon done button tap.
