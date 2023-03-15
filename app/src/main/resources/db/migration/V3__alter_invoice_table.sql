ALTER TABLE IF EXISTS public.invoice
    ADD COLUMN seller bigserial NOT NULL;

ALTER TABLE IF EXISTS public.invoice
    ADD COLUMN buyer bigserial NOT NULL;