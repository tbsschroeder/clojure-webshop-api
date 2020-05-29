CREATE TABLE public.article(
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    category TEXT NOT NULL,
    image TEXT NOT NULL
);
CREATE TABLE public.basket(
    id SERIAL PRIMARY KEY,
    article INTEGER REFERENCES article(id),
    count INTEGER DEFAULT 0 NOT NULL
);