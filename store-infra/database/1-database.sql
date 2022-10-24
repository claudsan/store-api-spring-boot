SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE public.item (
    item_id bigint NOT NULL,
    name character varying(255),
    quantity_avaiable bigint
);


ALTER TABLE public.item OWNER TO postgres;

CREATE SEQUENCE public.itemreq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.itemreq OWNER TO postgres;

CREATE SEQUENCE public.ordereq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ordereq OWNER TO postgres;

CREATE TABLE public.stock_mov (
    stock_mov_id bigint NOT NULL,
    creation_date timestamp without time zone,
    quantity bigint,
    item_id bigint NOT NULL,
    order_id bigint,
    type_movement character varying(3)
);

ALTER TABLE public.stock_mov OWNER TO postgres;

CREATE SEQUENCE public.stockmovreq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.stockmovreq OWNER TO postgres;

CREATE TABLE public.store_order (
    order_id bigint NOT NULL,
    creation_date timestamp without time zone,
    quantity bigint,
    item_id bigint NOT NULL,
    user_id bigint NOT NULL,
    quantity_served bigint,
    order_status character varying(30)
);

ALTER TABLE public.store_order OWNER TO postgres;

CREATE TABLE public.store_user (
    user_id bigint NOT NULL,
    email character varying(255),
    name character varying(255)
);

ALTER TABLE public.store_user OWNER TO postgres;

CREATE SEQUENCE public.userreq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.userreq OWNER TO postgres;

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (item_id);

ALTER TABLE ONLY public.stock_mov
    ADD CONSTRAINT stock_mov_pkey PRIMARY KEY (stock_mov_id);

ALTER TABLE ONLY public.store_order
    ADD CONSTRAINT store_order_pkey PRIMARY KEY (order_id);

ALTER TABLE ONLY public.store_user
    ADD CONSTRAINT store_user_pkey PRIMARY KEY (user_id);

ALTER TABLE ONLY public.store_order
    ADD CONSTRAINT order_item_id FOREIGN KEY (item_id) REFERENCES public.item(item_id);

ALTER TABLE ONLY public.store_order
    ADD CONSTRAINT order_user_id FOREIGN KEY (user_id) REFERENCES public.store_user(user_id);

ALTER TABLE ONLY public.stock_mov
    ADD CONSTRAINT stock_item_id FOREIGN KEY (item_id) REFERENCES public.item(item_id);
