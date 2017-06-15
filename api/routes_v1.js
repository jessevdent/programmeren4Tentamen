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

    var type = req.query.type;
    var key = req.query.key || '';
    var value = req.query.value || '';
    var limit = req.query.limit;

    res.contentType('application/json');

    if(type === undefined || type === '') {
        res.status(400);
        res.json({ error: 'Type is een verplichte parameter.'});
    }

    var query = 'SELECT * FROM  `1033`.\`' + type + '\`';

    if((key !== '') && (value !== '')) {
        query += ' WHERE \`' + key + '\`=' + db.escape(value);
    }

    if(limit !== undefined) {
        query += ' LIMIT ' + limit;
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


module.exports = routes;