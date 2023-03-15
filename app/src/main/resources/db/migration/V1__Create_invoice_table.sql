CREATE TABLE public.invoice
(
    id character varying(50) NOT NULL,
    date date NOT NULL,
    "number" character varying(50) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.invoice
    OWNER to postgres;