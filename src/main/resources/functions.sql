-- QUERY 1
CREATE OR REPLACE FUNCTION query_1
    (
      timeStart TIMESTAMP,
      timeEnd TIMESTAMP
    )
RETURNS
    TABLE(type VARCHAR, logcount BIGINT)
AS $$
    SELECT *
    FROM
    (
        SELECT 'access' AS type, COUNT(a.id) AS logcount
        FROM
            public.access AS a
        WHERE
            a.timestamp BETWEEN timeStart AND timeEnd
        UNION
        SELECT d.type, COUNT(d.id) AS logcount
        FROM
            public.dataxceiver AS d
        WHERE
            d.timestamp BETWEEN timeStart AND timeEnd
        GROUP BY
            d.type
        UNION
        SELECT n.type, COUNT(n.id) AS logcount
        FROM
            public.namesystem AS n
        WHERE
            n.timestamp BETWEEN timeStart AND timeEnd
        GROUP BY
            n.type
    ) AS un
    ORDER BY un.logcount DESC
$$ LANGUAGE SQL;

-- QUERY 2
CREATE OR REPLACE FUNCTION query_2
    (
      timeStart TIMESTAMP,
      timeEnd TIMESTAMP,
      typeIn VARCHAR
    )
RETURNS
    TABLE(date DATE, logcount BIGINT)
AS $$
    SELECT DATE(un.timestamp) as date, COUNT(un.id) AS logcount
    FROM
    (
        SELECT a.id, 'access' AS type, a.timestamp
        FROM
            public.access AS a
        WHERE
            a.timestamp BETWEEN timeStart AND timeEnd
        UNION
        SELECT d.id, d.type, d.timestamp
        FROM
            public.dataxceiver AS d
        WHERE
            d.timestamp BETWEEN timeStart AND timeEnd
        UNION
        SELECT n.id, n.type, n.timestamp
        FROM
            public.namesystem AS n
        WHERE
            n.timestamp BETWEEN timeStart AND timeEnd
    ) AS un
    WHERE un.type = typeIn
    GROUP BY DATE(un.timestamp)
    ORDER BY logcount DESC
$$ LANGUAGE SQL;

-- QUERY 3
CREATE OR REPLACE FUNCTION query_3
    (
      dateIn TIMESTAMP
    )
RETURNS
    TABLE(source_ip VARCHAR, logcount BIGINT)
AS $$
    SELECT un.source_ip, MAX(un.logcount) AS logcount
    FROM
    (
        SELECT COUNT(a.id) as logcount, s.source_ip, 'access' as type
        FROM
            public.access AS a,
            public.session As s
        WHERE
            s.id = a.session_id AND DATE(a.timestamp) = dateIn
        GROUP BY
            s.source_ip
        UNION
        SELECT COUNT(d.id) as logcount, d.source_ip, d.type
        FROM
            public.dataxceiver AS d
        WHERE
            DATE(d.timestamp) = dateIn
        GROUP BY
            d.source_ip, d.type
        UNION
        SELECT COUNT(n.id), n.source_ip, n.type
        FROM
            public.namesystem AS n
        WHERE
            DATE(n.timestamp) = dateIn
        GROUP BY
            n.source_ip, n.type
    ) AS un
    GROUP BY
        un.source_ip
    ORDER BY
        logcount DESC
$$ LANGUAGE SQL;

-- QUERY 4
CREATE OR REPLACE FUNCTION query_4
    (
      dateStart DATE,
      dateEnd DATE
    )
RETURNS
    TABLE(block_id BIGINT, actioncount NUMERIC)
AS $$
    SELECT un.block_id, SUM(un.actioncount) AS actioncount
    FROM
    (
        SELECT
            COUNT(d.id) AS actioncount, d.block_id
        FROM
            public.dataxceiver AS d
        WHERE
            DATE(d.timestamp) BETWEEN dateStart AND dateEnd
        GROUP BY
            d.block_id
        UNION
        SELECT COUNT(n.id) AS actioncount, b.block_id
        FROM
            public.namesystem AS n
        INNER JOIN
            public.block AS b
        ON
            b.namesystem_id = n.id
        WHERE
            DATE(n.timestamp) BETWEEN dateStart AND dateEnd
        GROUP BY
            b.block_id
    ) AS un
    GROUP BY
        un.block_id
    ORDER BY
        actioncount DESC
    LIMIT 5
$$ LANGUAGE SQL;

-- QUERY 5
CREATE OR REPLACE FUNCTION query_5 ()
RETURNS
    TABLE(referer VARCHAR, resourcecount BIGINT)
AS $$
    SELECT
        a.referer, COUNT(DISTINCT r.resource) AS resourcecount
    FROM
        public.access AS a
    INNER JOIN
        public.request AS r
    ON
        a.request_id = r.id
    GROUP BY
        a.referer
    HAVING
        COUNT(DISTINCT r.resource) > 1
    ORDER BY resourcecount DESC
$$ LANGUAGE SQL;

-- QUERY 6
CREATE OR REPLACE FUNCTION query_6 ()
RETURNS
    TABLE(resource VARCHAR, resourcecount BIGINT)
AS $$
    SELECT *
    FROM
    (
        SELECT
            r.resource, COUNT(*) as resourcecount
        FROM
            public.access AS a
        INNER JOIN
            public.request AS r
        ON
            a.request_id = r.id
        GROUP BY
            r.resource
        ORDER BY
            resourcecount DESC
        LIMIT 2) AS un
    ORDER BY
        un.resourcecount
    LIMIT 1
$$ LANGUAGE SQL;

-- QUERY 7
CREATE OR REPLACE FUNCTION query_7
    (
        sizeIn BIGINT
    )
RETURNS
    TABLE
    (
        id BIGINT,
        referer VARCHAR,
        timeOut TIMESTAMP,
        user_id VARCHAR,
        http_method VARCHAR,
        resource VARCHAR,
        size BIGINT,
        status INTEGER
    )
AS $$
    SELECT
        a.id, a.referer, a.timestamp, a.user_id, req.http_method, req.resource, res.size, res.status
    FROM
        public.access AS a
    INNER JOIN public.request AS req ON a.request_id = req.id
    INNER JOIN public.response AS res ON a.response_id = res.id
    WHERE
        res.size < sizeIn
    ORDER BY
        res.size DESC
$$ LANGUAGE SQL;

-- QUERY 8
CREATE OR REPLACE FUNCTION query_8 ()
RETURNS
    TABLE
    (
        block_id BIGINT
    )
AS $$
    SELECT DISTINCT
         b.block_id
     FROM
         public.dataxceiver AS d,
         public.namesystem AS n,
         public.block AS b
    WHERE
         n.id = b.namesystem_id AND
         d.type = 'served' AND
         n.type = 'replicate' AND
         b.block_id = d.block_id AND
         DATE(d.timestamp) = DATE(n.timestamp)
$$ LANGUAGE SQL;

-- QUERY 9
CREATE OR REPLACE FUNCTION query_9 ()
RETURNS
    TABLE
    (
        block_id BIGINT
    )
AS $$
    SELECT DISTINCT
         b.block_id
     FROM
         public.dataxceiver AS d,
         public.namesystem AS n,
         public.block AS b
    WHERE
         n.id = b.namesystem_id AND
         d.type = 'served' AND
         n.type = 'replicate' AND
         b.block_id = d.block_id AND
         DATE(d.timestamp) = DATE(n.timestamp) AND
         EXTRACT(HOUR FROM d.timestamp) = EXTRACT(HOUR FROM n.timestamp)
$$ LANGUAGE SQL;

-- QUERY 10
CREATE OR REPLACE FUNCTION query_10
    (
        browser VARCHAR
    )
RETURNS
    TABLE
    (
        id BIGINT
    )
AS $$
    SELECT
        a.id
    FROM
        public.access AS a
    INNER JOIN public.session AS s ON a.session_id = s.id
    INNER JOIN public.request AS req ON a.request_id = req.id
    INNER JOIN public.response AS res ON a.response_id = res.id
    WHERE
        s.user_agent LIKE CONCAT(browser,'%')
$$ LANGUAGE SQL;

-- QUERY 11
CREATE OR REPLACE FUNCTION query_11
    (
        method VARCHAR,
        timeStart TIMESTAMP,
        timeEnd TIMESTAMP
    )
RETURNS
    TABLE
    (
        source_ip VARCHAR
    )
AS $$
    SELECT DISTINCT
         s.source_ip
    FROM
         public.access AS a
    INNER JOIN public.session AS s ON a.session_id = s.id
    INNER JOIN public.request AS req ON a.request_id = req.id
    WHERE
         req.http_method = method AND
         a.timestamp BETWEEN timeStart AND timeEnd
$$ LANGUAGE SQL;

-- QUERY 12
CREATE OR REPLACE FUNCTION query_12
    (
        method1 VARCHAR,
        method2 VARCHAR,
        timeStart TIMESTAMP,
        timeEnd TIMESTAMP
    )
RETURNS
    TABLE
    (
        source_ip VARCHAR
    )
AS $$
    SELECT
         s.source_ip
    FROM
         public.access AS a
    INNER JOIN public.session AS s ON a.session_id = s.id
    INNER JOIN public.request AS req ON a.request_id = req.id
    WHERE
         req.http_method = method1 OR
         req.http_method = method2 AND
         a.timestamp BETWEEN timeStart AND timeEnd
    GROUP BY
        s.source_ip
    HAVING
        COUNT(DISTINCT req.http_method) = 2
$$ LANGUAGE SQL;

-- QUERY 13
CREATE OR REPLACE FUNCTION query_13
    (
        timeStart TIMESTAMP,
        timeEnd TIMESTAMP
    )
RETURNS
    TABLE
    (
        source_ip VARCHAR
    )
AS $$
    SELECT
         s.source_ip
    FROM
         public.access AS a
    INNER JOIN public.session AS s ON a.session_id = s.id
    INNER JOIN public.request AS req ON a.request_id = req.id
    WHERE
         a.timestamp BETWEEN timeStart AND timeEnd
    GROUP BY
        s.source_ip
    HAVING
        COUNT(DISTINCT req.http_method) >= 4
$$ LANGUAGE SQL;
