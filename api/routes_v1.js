var express = require('express');
var routes = express.Router();
var db = require('../config/db');

routes.get('/hello', function(req, res){
	res.contentType('application/json');
	res.status(200);
	res.json({ 'tekst': 'Hello!'});
});

routes.get('/goodbye', function(req, res){
	res.contentType('application/json');
	res.status(200);
	res.json({ 'tekst': 'Goodbye!'});
});

routes.get('/films/:filmid', function(req, res){

    var film_id = req.params.filmid;

    res.contentType('application/json');

    db.query('SELECT * FROM `1033`.`film`,`1033`.`rental` WHERE film_id=?', [ film_id ], function(error, rows, fields) {
        if (error) {
            res.status(400);
            res.json({ error: 'Error while performing Query.'});
        } else {
            res.status(200);
            res.json(rows);
        };
    });
});

routes.get('/rentals/:userid', function(req, res){

    var customer_id = req.params.userid;

    res.contentType('application/json');

    db.query('SELECT film.title FROM `1033`.`film`, `1033`.`rental`, `1033`.`inventory` WHERE rental.customer_id=? AND rental.inventory_id = inventory.inventory_id AND inventory.film_id = film.film_id', [ customer_id ], function(error, rows, fields) {
        if (error) {
            res.status(400);
            res.json({ error: 'Error while performing Query.'});
        } else {
            res.status(200);
            res.json(rows);
        };
    });
});

routes.get('/film/:limit/:offset', function(req, res){

    var limit = req.params.limit;
    var offset = req.params.offset;

    res.contentType('application/json');

    db.query('SELECT * FROM `1033`.`film` ORDER BY `film_id` ASC LIMIT=? OFFSET=?', [limit, offset], function(error, rows, fields) {
        if (error) {
            res.status(400);
            res.json({ error: 'Error while performing Query.'});
        } else {
            res.status(200);
            res.json(rows);
        };
    });
});

routes.post('/rentals/insert', function(req, res) {

    var rentals = req.body;
    var query = {
        sql: 'INSERT INTO `1033`.`rental`(`rental_date`, `inventory_id`, `customer_id`) VALUES(CURRENT_TIMESTAMP, ?, ?)',
        values: [rentals.inventoryid, rentals.userid],
        timeout: 2000
    };

    console.dir(rentals);
    console.log('Onze query: ' + query.sql);

    res.contentType('application/json');
    db.query(query, function(error, rows, fields) {
        if (error) {
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });
});

routes.put('/rentals/update', function(req, res) {

    var rentals = req.body;
    var query = {
        sql: 'UPDATE `1033`.`rental` SET `staff_id` = ?, `return_date` = ? WHERE `inventory_id` = ? AND `customer_id` = ?',
        values: [rentals.staffid, rentals.returndate, rentals.inventoryid, rentals.userid],
        timeout: 2000
    };

    console.dir(rentals);
    console.log('Onze query: ' + query.sql);

    res.contentType('application/json');
    db.query(query, function(error, rows, fields) {
        if (error) {
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });
});

routes.delete('/rentals/delete', function(req, res) {

    var rentals = req.body;
    var query = {
        sql: 'DELETE FROM `1033`.`rental` WHERE `inventory_id` = ? AND `customer_id` = ?',
        values: [rentals.inventoryid, rentals.userid],
        timeout: 2000
    };

    console.dir(rentals);
    console.log('Onze query: ' + query.sql);

    res.contentType('application/json');
    db.query(query, function(error, rows, fields) {
        if (error) {
            res.status(401).json(error);
        } else {
            res.status(200).json({ result: rows });
        };
    });
});
module.exports = routes;