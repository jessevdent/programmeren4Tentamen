var chai = require('chai');
var chaiHttp = require('chai-http');
var server = require('../server.js');
var should = chai.should();

chai.use(chaiHttp);

describe('Hello Test', function() {
 	it('Test GET /api/v1/hello', function(done) {
 		chai.request(server)
 			.get('/api/v1/hello')
            .set({	"Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTgxMzY0OTgsImlhdCI6MTQ5NzUzMTY5OCwic3ViIjoiVGVzdDEifQ.n93eyWMgdY5bVnTRdEeBzceolmLwu_pRsJ-w_9i5C7g"})
 			.end(function(err, res) {
				 res.should.have.status(200);
				 res.body.should.be.a('object');
                 res.body.should.have.property('tekst');
                 res.body.tekst.should.equal('Hello!');
				 done();
 			});
 	});
});

describe('Goodbye Test', function() {
    it('Test GET /api/v1/goodbye', function(done) {
        chai.request(server)
            .get('/api/v1/goodbye')
            .set({	"Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTgxMzY0OTgsImlhdCI6MTQ5NzUzMTY5OCwic3ViIjoiVGVzdDEifQ.n93eyWMgdY5bVnTRdEeBzceolmLwu_pRsJ-w_9i5C7g"})
            .end(function(err, res) {
                res.should.have.status(200);
                res.body.should.be.a('object');
                res.body.should.have.property('tekst');
                res.body.tekst.should.equal('Goodbye!');
                done();
            });
    });
});

describe('Register Test', function() {
    it('Test post /api/v1/register', function(done) {
        chai.request(server)
            .post('/api/v1/register')
            .send({	"username": "Mocha",
                "password": "Chai",
                "first_name": "Mocha",
                "last_name": "Chai",
                "email": "mocha@chai.nl",
                "address": "MochaChaiLaan",
                "postal_code": "1234AB",
                "city": "Breda",
                "country": "The Netherlands",
                "house_number": 1  })
            .end(function(err, res){
                res.should.have.status(200);
                res.should.be.json;
                res.body.should.be.a('object');

                res.body.should.have.property('result');
                res.body.result.should.be.a('object');

                res.body.result.should.have.property('fieldCount');
                res.body.result.should.have.property('affectedRows');
                res.body.result.should.have.property('insertId');
                res.body.result.should.have.property('serverStatus');
                res.body.result.should.have.property('warningCount');
                res.body.result.should.have.property('message');
                res.body.result.should.have.property('protocol41');
                res.body.result.should.have.property('changedRows');

                res.body.result.fieldCount.should.equal(0);
                res.body.result.affectedRows.should.equal(1);
                res.body.result.serverStatus.should.equal(2);
                res.body.result.warningCount.should.equal(0);
                res.body.result.message.should.equal('');
                res.body.result.protocol41.should.equal(true);
                res.body.result.changedRows.should.equal(0);

                done();
            });
    });
});

describe('Login Test', function() {
    it('Test post /api/v1/login', function(done) {
        chai.request(server)
            .post('/api/v1/login')
            .send({	"username": "Mocha",
                "password": "Chai"})
            .end(function(err, res){
                res.should.have.status(200);
                res.should.be.json;
                res.body.should.be.a('object');

                res.body.should.have.property('token');

                done();
            });
    });
});

describe('films Test', function() {
    it('Test GET /api/v1/films', function(done) {
        chai.request(server)
            .get('/api/v1/films')
            .end(function(err, res) {
                res.should.have.status(200);
                res.body.should.be.a('array');
                done();
            });
    });
});

describe('Film Limit & Offset Test', function() {
    it('Test GET /api/v1/films?limit=1&offset=0', function(done) {
        chai.request(server)
            .get('/api/v1/films?limit=1&offset=0')
            .set({	"Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTgxMzY0OTgsImlhdCI6MTQ5NzUzMTY5OCwic3ViIjoiVGVzdDEifQ.n93eyWMgdY5bVnTRdEeBzceolmLwu_pRsJ-w_9i5C7g"})
            .end(function(err, res) {
                res.should.have.status(200);
                res.body.should.be.a('object');

                res.body.should.have.property('film_id');
                res.body.should.have.property('title');
                res.body.should.have.property('description');
                res.body.should.have.property('release_year');
                res.body.should.have.property('language_id');
                res.body.should.have.property('original_language_id');
                res.body.should.have.property('rental_duration');
                res.body.should.have.property('rental_rate');
                res.body.should.have.property('length');
                res.body.should.have.property('replacement_cost');
                res.body.should.have.property('rating');
                res.body.should.have.property('special_features');
                res.body.should.have.property('last_update');

                res.body.film_id.should.equal('1');
                res.body.title.should.equal('ACADEMY DINOSAUR');
                res.body.description.should.equal("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies");
                res.body.release_year.should.equal('2006');
                res.body.language_id.should.equal('1');
                res.body.original_language_id.should.equal(null);
                res.body.rental_duration.should.equal(6);
                res.body.rental_rate.should.equal(0.99);
                res.body.length.should.equal(86);
                res.body.replacement_cost.should.equal(20.99);
                res.body.rating.should.equal("PG");
                res.body.special_features.should.equal('Deleted Scenes,Behind the Scenes');
                res.body.special_features.should.equal('2006-02-15T04:03:42.000Z');
                done();
            });
    });
});

describe('Film Select Test', function() {
    it('Test GET /api/v1/films/1', function(done) {
        chai.request(server)
            .get('/api/v1/films/1')
            .end(function(err, res) {
                res.should.have.status(200);
                res.body.should.be.a('object');

                res.body.should.have.property('film_id');
                res.body.should.have.property('title');
                res.body.should.have.property('description');
                res.body.should.have.property('release_year');
                res.body.should.have.property('language_id');
                res.body.should.have.property('original_language_id');
                res.body.should.have.property('rental_duration');
                res.body.should.have.property('rental_rate');
                res.body.should.have.property('length');
                res.body.should.have.property('replacement_cost');
                res.body.should.have.property('rating');
                res.body.should.have.property('special_features');
                res.body.should.have.property('last_update');

                res.body.film_id.should.equal('1');
                res.body.title.should.equal('ACADEMY DINOSAUR');
                res.body.description.should.equal("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies");
                res.body.release_year.should.equal('2006');
                res.body.language_id.should.equal('1');
                res.body.original_language_id.should.equal(null);
                res.body.rental_duration.should.equal(6);
                res.body.rental_rate.should.equal(0.99);
                res.body.length.should.equal(86);
                res.body.replacement_cost.should.equal(20.99);
                res.body.rating.should.equal("PG");
                res.body.special_features.should.equal('Deleted Scenes,Behind the Scenes');
                res.body.special_features.should.equal('2006-02-15T04:03:42.000Z');
                done();
            });
    });
});

describe('Rentals Test', function() {
    it('Test GET /api/v1/rentals/2', function(done) {
        chai.request(server)
            .get('/api/v1/rentals/2')
            .set({	"Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTgxMzY0OTgsImlhdCI6MTQ5NzUzMTY5OCwic3ViIjoiVGVzdDEifQ.n93eyWMgdY5bVnTRdEeBzceolmLwu_pRsJ-w_9i5C7g"})
            .end(function(err, res) {
                res.should.have.status(200);
                res.body.should.be.a('array');

                res.body[0].should.have.proporty('title');
                res.body[1].should.have.proporty('title');
                res.body[2].should.have.proporty('title');

                res.body[0].title.should.equal('DOORS PRESIDENT');
                res.body[1].title.should.equal('BLACKOUT PRIVATE');
                res.body[2].title.should.equal('TOMORROW HUSTLER');

                done();
            });
    });
});

describe('New Rental Test', function() {
it('Test post /api/v1/rentals/45/1', function(done) {
    chai.request(server)
        .post('/api/v1/rentals/45/1')
        .set({	"Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTgxMzY0OTgsImlhdCI6MTQ5NzUzMTY5OCwic3ViIjoiVGVzdDEifQ.n93eyWMgdY5bVnTRdEeBzceolmLwu_pRsJ-w_9i5C7g"})
        .end(function(err, res){
            res.should.have.status(200);
            res.should.be.json;
            res.body.should.be.a('object');

            res.body.should.have.property('result');
            res.body.result.should.be.a('object');

            res.body.result.should.have.property('fieldCount');
            res.body.result.should.have.property('affectedRows');
            res.body.result.should.have.property('insertId');
            res.body.result.should.have.property('serverStatus');
            res.body.result.should.have.property('warningCount');
            res.body.result.should.have.property('message');
            res.body.result.should.have.property('protocol41');
            res.body.result.should.have.property('changedRows');

            res.body.result.fieldCount.should.equal(0);
            res.body.result.affectedRows.should.equal(1);
            res.body.result.serverStatus.should.equal(2);
            res.body.result.warningCount.should.equal(0);
            res.body.result.message.should.equal('');
            res.body.result.protocol41.should.equal(true);
            res.body.result.changedRows.should.equal(0);

            done();
        });
	});
});

describe('Adjust Rental Test', function() {
    it('Test put /api/v1/rentals/45/1', function(done) {
        chai.request(server)
            .put('/api/v1/rentals/45/1')
            .send({	'staffid': 2, 'returndate': '2017-10-10 10:10:10'})
            .set({	"Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTgxMzY0OTgsImlhdCI6MTQ5NzUzMTY5OCwic3ViIjoiVGVzdDEifQ.n93eyWMgdY5bVnTRdEeBzceolmLwu_pRsJ-w_9i5C7g"})
            .end(function(err, res){
                res.should.have.status(200);
                res.should.be.json;
                res.body.should.be.a('object');

                res.body.should.have.property('result');
                res.body.result.should.be.a('object');

                res.body.result.should.have.property('fieldCount');
                res.body.result.should.have.property('affectedRows');
                res.body.result.should.have.property('insertId');
                res.body.result.should.have.property('serverStatus');
                res.body.result.should.have.property('warningCount');
                res.body.result.should.have.property('message');
                res.body.result.should.have.property('protocol41');
                res.body.result.should.have.property('changedRows');

                res.body.result.fieldCount.should.equal(0);
                res.body.result.affectedRows.should.equal(2);
                res.body.result.insertId.should.equal(0);
                res.body.result.serverStatus.should.equal(2);
                res.body.result.warningCount.should.equal(0);
                res.body.result.message.should.equal('(Rows matched: 2  Changed: 2  Warnings: 0');
                res.body.result.protocol41.should.equal(true);
                res.body.result.changedRows.should.equal(2);

                done();
            });
    });
});

describe('Delete Rental Test', function() {
    it('Test delete /api/v1/rentals/45/1', function(done) {
        chai.request(server)
            .delete('/api/v1/rentals/45/1')
            .set({	"Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0OTgxMzY0OTgsImlhdCI6MTQ5NzUzMTY5OCwic3ViIjoiVGVzdDEifQ.n93eyWMgdY5bVnTRdEeBzceolmLwu_pRsJ-w_9i5C7g"})
            .end(function(err, res){
                res.should.have.status(200);
                res.should.be.json;
                res.body.should.be.a('object');

                res.body.should.have.property('result');
                res.body.result.should.be.a('object');

                res.body.result.should.have.property('fieldCount');
                res.body.result.should.have.property('affectedRows');
                res.body.result.should.have.property('insertId');
                res.body.result.should.have.property('serverStatus');
                res.body.result.should.have.property('warningCount');
                res.body.result.should.have.property('message');
                res.body.result.should.have.property('protocol41');
                res.body.result.should.have.property('changedRows');

                res.body.result.fieldCount.should.equal(0);
                res.body.result.affectedRows.should.equal(2);
                res.body.result.insertId.should.equal(0);
                res.body.result.serverStatus.should.equal(2);
                res.body.result.warningCount.should.equal(0);
                res.body.result.message.should.equal('');
                res.body.result.protocol41.should.equal(true);
                res.body.result.changedRows.should.equal(0);

                done();
            });
    });
});