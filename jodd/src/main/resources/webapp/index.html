<!doctype html>
<html lang="en">
<head>
    <title>My Grocery Lists</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/ng-admin@1.0.12/build/ng-admin.min.css">
</head>
<body ng-app="myApp">
<div ui-view="ng-admin"></div>
<script src="https://cdn.jsdelivr.net/npm/ng-admin@1.0.12/build/ng-admin.min.js"></script>
<script type="text/javascript">
    var myApp = angular.module('myApp', ['ng-admin']);
    myApp.config(['NgAdminConfigurationProvider', function (NgAdminConfigurationProvider) {
        var nga = NgAdminConfigurationProvider;
        // create an admin application
        var admin = nga.application('My Grocery Lists');

        var configureFields = function (view) {
            return view.fields([
                nga.field('id').editable(false),
                nga.field('comment'),
                nga.field('settled', 'boolean'),
                nga.field('date', 'date'),
                nga.field('shoppingItems', 'embedded_list')
                        .targetFields([
                            nga.field('quantity', 'number'),
                            nga.field('unit', 'choice').choices([
                                {value: 'pcs', label: 'pieces'},
                                {value: 'kg', label: 'kilogram'},
                                {value: 'g', label: 'gram'},
                                {value: 'l', label: 'litre'}
                            ]),
                            nga.field('name', 'string')
                        ])
            ]);
        };

        // lists entity
        var list = nga.entity('list');

        list.listView()
                .fields([
                    nga.field('comment'),
                    nga.field('settled', 'boolean'),
                    nga.field('date', 'date')])
                .listActions(['show', 'edit', 'delete'])
                .title("My shopping lists");

        configureFields(list.editionView())
                .title('Edit "{{ entry.values.comment }}"')
                .actions(['show', 'list', 'delete']);
        configureFields(list.showView())
                .title('View "{{ entry.values.comment }}"');
        configureFields(list.creationView());

        admin.addEntity(list);

        // attach the admin application to the DOM and run it
        nga.configure(admin);
    }]);
</script>
<style>
    #page-wrapper { margin: 0px 0px 0px 150px; }
    .sidebar { width: 150px; }
</style>
</body>
</html>