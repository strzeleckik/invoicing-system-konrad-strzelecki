CREATE TABLE public.invoice
(
    id character varying(50) NOT NULL,
    date date NOT NULL,
    "number" character varying(50) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.invoice
    OWNER to postgres;

CREATE TABLE public.invoice_entry
(
    id character varying(50) NOT NULL,
    description integer NOT NULL,
    price numeric NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.invoice_entry
    OWNER to postgres;