CREATE TABLE public.article(
    id SERIAL,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    category TEXT NOT NULL,
    image TEXT NOT NULL,
    count INTEGER DEFAULT 0 NOT NULL
);