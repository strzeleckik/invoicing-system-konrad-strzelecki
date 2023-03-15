CREATE TABLE public.company
(
    id bigserial NOT NULL,
    "taxId" character varying(50) NOT NULL,
    address character varying(100) NOT NULL,
    name character varying(100) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.company
    OWNER to postgres;