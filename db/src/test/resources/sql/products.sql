-- Table: public.products

-- DROP TABLE public.products;

CREATE TABLE public.products
(
  isin character(12) NOT NULL,
  cfid character varying(40) NOT NULL,
  name character varying(40) NOT NULL,
  last_price double precision,
  CONSTRAINT firstkey PRIMARY KEY (isin)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.products
  OWNER TO postgres;
