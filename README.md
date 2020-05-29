# WebShop CRUD Api with Compojure

* compojure
* component
* toucan

## Usage

**TL;DR:** `docker-compose up`, otherwise:

The app needs a postgres database. You can create a database with correct
settings via:

```
docker build -t db -f ./Dockerfile.db .
docker run --rm -p 5432:5432 db
```

This will also setup an entry point and create the tables, otherwise the service will take care.

Then you can start the system the system like mentioned below.

### Run the application locally

`lein run`

### Run the tests

`lein midje`

### Packaging and running as standalone jar

```
lein do clean, ring uberjar
java -jar target/server.jar
```

### Packaging as war

`lein ring uberwar`

### Helper

You can run multiple helping tools like code formatting, linting, interactive 
dependency checker and code analyzer via:

    lein cljfmt fix
    lein eastwood
    lein ancient upgrade :interactive
    lein kibit

## License
___
*All images are under the copyright of METRO AG.*
___

The MIT License (MIT)

*Copyright © 2020 Dr. Tobias Schröder*

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
