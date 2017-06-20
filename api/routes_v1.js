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

routes.get('/films', function(req, res){

    var limit = req.query.limit;
    var offset = req.query.offset;

    res.contentType('application/json');

    var query = 'SELECT inventory.inventory_id, film.title, film.description, film.special_features, film.release_year, film.rating, film.length, film.rental_duration, film.rental_rate, film.replacement_cost, film.film_id from `1033`.film, `1033`.inventory,  `1033`.rental where film.film_id = inventory.film_id and inventory.available = 0';

    if(limit !== undefined) {
        query += ' LIMIT ' + limit;
    }

    if(offset !== undefined) {
        query += ' OFFSET ' + offset;
    }

    console.log('Onze query: ' + query);
    +

        db.query(query, function(error, rows, fields) {
            if (error) {
                res.status(400);
                res.json(error);
            } else {
                res.status(200);
                res.json(rows);
            };
        });
});

routes.get('/films/:filmid', function(req, res){

    var film_id = req.params.filmid;

    res.contentType('application/json');

    db.query('SELECT * FROM `1033`.`film` WHERE film_id=?', [ film_id ], function(error, rows, fields) {
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

    db.query('SELECT `view_rental`.`title`, `view_rental`.`inventory_id` FROM `1033`.`view_rental`, `1033`.`rental` WHERE rental.rental_id = view_rental.rental_id AND rental.customer_id=? AND view_rental.active = 1', [ customer_id ], function(error, rows, fields) {
        if (error) {
            res.status(400);
            res.json({ error: 'Error while performing Query.'});
        } else {
            res.status(200);
            res.json(rows);
        };
    });
});


routes.post('/rentals/:userid/:inventoryid', function(req, res) {

    var user_id = req.params.userid;
    var inventory_id = req.params.inventoryid;

    res.contentType('application/json');
    var query = {
        sql: 'begin; INSERT INTO `1033`.`rental`(`rental_date`, `inventory_id`, `customer_id`) VALUES(CURRENT_TIMESTAMP, ?, ?); commit; begin; update `1033`.`inventory` inner JOIN `1033`.rental on  inventory.inventory_id = rental.inventory_id inner join customer on customer.customer_id = rental.customer_id SET available = 1; commit;',
        values: [inventory_id, user_id],
        timeout: 2000
    };

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

routes.put('/rentals/:userid/:inventoryid', function(req, res) {

    var user_id = req.params.userid;
    var inventory_id = req.params.inventoryid;
    var rentals = req.body;

    var query = {
        sql: 'UPDATE `1033`.`rental` SET `staff_id` = ?, `return_date` = ? WHERE `inventory_id` = ? AND `customer_id` = ?',
        values: [rentals.staffid, rentals.returndate, inventory_id, user_id],
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

routes.delete('/rentals/:userid/:inventoryid', function(req, res) {

    var user_id = req.params.userid;
    var inventory_id = req.params.inventoryid;
    var query = {
        sql: 'DELETE FROM `1033`.`rental` WHERE `inventory_id` = ? AND `customer_id` = ?',
        values: [inventory_id, user_id],
        timeout: 2000
    };

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