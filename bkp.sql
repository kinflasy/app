--
-- PostgreSQL database dump
--

\restrict zx9mxUIu4KOXCOBqqeJaA3wO1GEWtX0SXLXxhcuv1879PdlPj5HoBC2ABZX66Fg

-- Dumped from database version 18.3 (Debian 18.3-1.pgdg13+1)
-- Dumped by pg_dump version 18.4 (Ubuntu 18.4-1.pgdg24.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: abilities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.abilities (
    id uuid NOT NULL,
    role_id uuid NOT NULL,
    user_id uuid NOT NULL,
    person_id uuid NOT NULL
);


ALTER TABLE public.abilities OWNER TO postgres;

--
-- Name: addresses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.addresses (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    city character varying(255),
    complement character varying(255),
    country character varying(255),
    neighborhood character varying(255),
    number character varying(255),
    reference character varying(255),
    state character varying(255),
    street character varying(255),
    zip character varying(255)
);


ALTER TABLE public.addresses OWNER TO postgres;

--
-- Name: calendar_event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.calendar_event (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    card_image_id uuid,
    description text,
    end_date_time timestamp(6) without time zone NOT NULL,
    start_date_time timestamp(6) without time zone NOT NULL,
    title character varying(255) NOT NULL
);


ALTER TABLE public.calendar_event OWNER TO postgres;

--
-- Name: churches; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.churches (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    acronym character varying(255),
    email character varying(255) NOT NULL,
    email_verified_at timestamp(6) without time zone,
    name character varying(255) NOT NULL,
    phone character varying(255),
    slug character varying(255) NOT NULL
);


ALTER TABLE public.churches OWNER TO postgres;

--
-- Name: department_calendar_events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.department_calendar_events (
    department_id uuid NOT NULL,
    id uuid NOT NULL
);


ALTER TABLE public.department_calendar_events OWNER TO postgres;

--
-- Name: departments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.departments (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    name character varying(255) NOT NULL,
    slug character varying(255) NOT NULL,
    type character varying(255) NOT NULL,
    unit_id uuid NOT NULL,
    visibility_id uuid,
    cover_image_id uuid,
    profile_image_id uuid,
    CONSTRAINT departments_type_check CHECK (((type)::text = ANY ((ARRAY['ADMINISTRATIVE'::character varying, 'MINISTRY'::character varying])::text[])))
);


ALTER TABLE public.departments OWNER TO postgres;

--
-- Name: extensions_subscriptions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.extensions_subscriptions (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    department_id uuid NOT NULL,
    extension character varying(255) NOT NULL,
    CONSTRAINT extensions_subscriptions_extension_check CHECK (((extension)::text = 'SOMA'::text))
);


ALTER TABLE public.extensions_subscriptions OWNER TO postgres;

--
-- Name: inactive_people; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.inactive_people (
    church_id uuid NOT NULL,
    email character varying(255),
    id uuid NOT NULL
);


ALTER TABLE public.inactive_people OWNER TO postgres;

--
-- Name: integrations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.integrations (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    department_id uuid NOT NULL,
    membership_id uuid NOT NULL,
    type character varying(255) NOT NULL,
    CONSTRAINT integrations_type_check CHECK (((type)::text = ANY ((ARRAY['OBSERVER'::character varying, 'CONSULTANT'::character varying, 'INTEGRANT'::character varying, 'ASSISTANT'::character varying, 'LEADER'::character varying])::text[])))
);


ALTER TABLE public.integrations OWNER TO postgres;

--
-- Name: links; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.links (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    label character varying(255) NOT NULL,
    url character varying(255) NOT NULL
);


ALTER TABLE public.links OWNER TO postgres;

--
-- Name: media; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.media (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    file_size bigint,
    mime_type character varying(255),
    original_filename character varying(255)
);


ALTER TABLE public.media OWNER TO postgres;

--
-- Name: memberships; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.memberships (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    affiliation character varying(255) NOT NULL,
    entry_date date,
    entry_mode character varying(255),
    leave_date date,
    leave_mode character varying(255),
    leave_note character varying(255),
    person_id uuid NOT NULL,
    unit_id uuid NOT NULL,
    CONSTRAINT memberships_affiliation_check CHECK (((affiliation)::text = ANY ((ARRAY['VISITOR'::character varying, 'CONGREGATED'::character varying, 'MEMBER'::character varying])::text[]))),
    CONSTRAINT memberships_entry_mode_check CHECK (((entry_mode)::text = ANY ((ARRAY['BAPTISM'::character varying, 'TRANSFER'::character varying, 'REGRESS'::character varying, 'ACCLAIM'::character varying])::text[]))),
    CONSTRAINT memberships_leave_mode_check CHECK (((leave_mode)::text = ANY ((ARRAY['DECEASE'::character varying, 'TRANSFER'::character varying, 'REQUEST'::character varying, 'ABANDONMENT'::character varying, 'REMOVAL'::character varying])::text[])))
);


ALTER TABLE public.memberships OWNER TO postgres;

--
-- Name: openfga_migrations_logs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.openfga_migrations_logs (
    id uuid NOT NULL,
    description character varying(255) NOT NULL,
    details text,
    executed_at timestamp(6) without time zone,
    status character varying(255) NOT NULL,
    version integer NOT NULL,
    CONSTRAINT openfga_migrations_logs_status_check CHECK (((status)::text = ANY ((ARRAY['SUCCESS'::character varying, 'FAILED'::character varying, 'ROLLED_BACK'::character varying])::text[])))
);


ALTER TABLE public.openfga_migrations_logs OWNER TO postgres;

--
-- Name: pending_integrations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pending_integrations (
    id uuid NOT NULL,
    department_id uuid NOT NULL,
    membership_id uuid NOT NULL
);


ALTER TABLE public.pending_integrations OWNER TO postgres;

--
-- Name: pending_memberships; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pending_memberships (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    affiliation character varying(255),
    entry_date date,
    entry_mode character varying(255),
    person_id uuid NOT NULL,
    unit_confirmation_date timestamp(6) without time zone,
    unit_id uuid NOT NULL,
    user_confirmation_date timestamp(6) without time zone,
    CONSTRAINT pending_memberships_affiliation_check CHECK (((affiliation)::text = ANY ((ARRAY['VISITOR'::character varying, 'CONGREGATED'::character varying, 'MEMBER'::character varying])::text[]))),
    CONSTRAINT pending_memberships_entry_mode_check CHECK (((entry_mode)::text = ANY ((ARRAY['BAPTISM'::character varying, 'TRANSFER'::character varying, 'REGRESS'::character varying, 'ACCLAIM'::character varying])::text[])))
);


ALTER TABLE public.pending_memberships OWNER TO postgres;

--
-- Name: people; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.people (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    address_id uuid,
    birth_date date NOT NULL,
    full_name character varying(255) NOT NULL,
    gender character varying(255) NOT NULL,
    nickname character varying(255),
    phone character varying(255),
    profile_image_id uuid,
    CONSTRAINT people_gender_check CHECK (((gender)::text = ANY ((ARRAY['MALE'::character varying, 'FEMALE'::character varying])::text[])))
);


ALTER TABLE public.people OWNER TO postgres;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    slug character varying(255) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: unit_calendar_events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unit_calendar_events (
    unit_id uuid NOT NULL,
    id uuid NOT NULL
);


ALTER TABLE public.unit_calendar_events OWNER TO postgres;

--
-- Name: unit_links; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unit_links (
    link_id uuid NOT NULL,
    unit_id uuid NOT NULL
);


ALTER TABLE public.unit_links OWNER TO postgres;

--
-- Name: units; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.units (
    id uuid NOT NULL,
    created_by uuid,
    created_date timestamp(6) without time zone,
    last_modified_by uuid,
    last_modified_date timestamp(6) without time zone,
    address_id uuid NOT NULL,
    church_id uuid NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    slug character varying(255) NOT NULL,
    type character varying(255) NOT NULL,
    cover_image_id uuid,
    profile_image_id uuid,
    CONSTRAINT units_type_check CHECK (((type)::text = ANY ((ARRAY['MAIN'::character varying, 'BRANCH'::character varying])::text[])))
);


ALTER TABLE public.units OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    email character varying(255) NOT NULL,
    email_verified_at timestamp(6) without time zone,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    id uuid NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: abilities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.abilities (id, role_id, user_id, person_id) FROM stdin;
\.


--
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.addresses (id, created_by, created_date, last_modified_by, last_modified_date, city, complement, country, neighborhood, number, reference, state, street, zip) FROM stdin;
07ddc527-18d0-4ee7-9a6d-6efa527c15ec	adcaa634-0f41-4135-ad80-d6e22ebb2533	2026-04-15 18:17:52.324135	\N	\N	Mossoró	\N	Brasil	Alto De São Manuel	123	\N	Rio Grande Do Norte	Prof. Chagas	59600233
0de09b99-75c3-404a-a77f-bf5db33492ea	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 19:12:44.434879	\N	\N	Mo	\N	Br	Sumare	165	\N	Rn	Joao Nep	58633200
08c6845c-2859-4b86-bae3-ede98ea1bfb0	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 01:32:07.307728	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-02 20:14:28.075185	Mossoró	Da Esquina	Brasil	Planalto 13 De Maio	370	\N	Rio Grande Do Norte	Martin Júnior	59631350
\.


--
-- Data for Name: calendar_event; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.calendar_event (id, created_by, created_date, last_modified_by, last_modified_date, card_image_id, description, end_date_time, start_date_time, title) FROM stdin;
664eeb5e-12e4-4165-8490-578a7accfdfa	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-12 00:34:07.636112	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-12 03:21:42.433684	b56a2700-dc9b-41f0-aa80-9fe5ea9ee1dc	vai ser super legal	2026-05-30 23:33:00	2026-05-30 09:30:00	Reunião das mensageiras do Rei
89bd39a7-31ed-4262-8aaf-c1426593a6ca	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-12 02:26:40.918569	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-12 16:12:03.070656	5eb6242f-67ad-4622-8089-052ed2152ec2	um dia para aprender sobre mídia e tecnologias usadas nas igrejas e especializar seu trabalho com o que há de melhor e mais eficiente.	2026-05-11 23:55:00	2026-05-11 23:00:00	Mídia day
fe3ae06f-bb27-4d6d-b833-7c9fb1e93227	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-12 17:34:33.174564	\N	\N	\N	Discutir direito de imagem do público, compra de webcam e introdução da plataforma Pontis na administração da igreja	2026-06-03 18:00:00	2026-06-03 16:00:00	Reunião interna
\.


--
-- Data for Name: churches; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.churches (id, created_by, created_date, last_modified_by, last_modified_date, acronym, email, email_verified_at, name, phone, slug) FROM stdin;
20c3d107-4b47-40a9-a378-084b0e490201	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 01:32:07.17655	\N	\N	IBB	ibbmossoro@email.com	\N	Igreja Batista Betel	84963332222	ibbmossoro
a75ffd55-a9f6-46ed-88d9-12a0f0f9a504	adcaa634-0f41-4135-ad80-d6e22ebb2533	2026-04-15 18:17:51.415228	\N	\N	IBF	ibf@email.com	\N	Igreja Batista Da Fé	84988884444	ibfmo
\.


--
-- Data for Name: department_calendar_events; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.department_calendar_events (department_id, id) FROM stdin;
356dc720-18ca-4e66-a2e2-bfba88b15581	89bd39a7-31ed-4262-8aaf-c1426593a6ca
a80d5151-a1c0-4eae-99e4-6b068ac2c762	fe3ae06f-bb27-4d6d-b833-7c9fb1e93227
\.


--
-- Data for Name: departments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.departments (id, created_by, created_date, last_modified_by, last_modified_date, name, slug, type, unit_id, visibility_id, cover_image_id, profile_image_id) FROM stdin;
545e21ad-c1b3-4ec8-a08a-22af73194b20	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 01:32:07.548916	\N	\N	Ministério Pastoral	pastoral	ADMINISTRATIVE	32c01372-c748-4e05-b348-aa0830a9429b	\N	\N	\N
729bdee6-db1d-45b8-acd9-344f2ad3ed67	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 01:32:07.587638	\N	\N	Secretaria	secretaria	ADMINISTRATIVE	32c01372-c748-4e05-b348-aa0830a9429b	\N	\N	\N
90a10a02-bf27-4fe0-b0db-4297e1803f7c	adcaa634-0f41-4135-ad80-d6e22ebb2533	2026-04-15 18:17:54.169666	\N	\N	Ministério Pastoral	pastoral	ADMINISTRATIVE	21827ae6-3a67-4316-8aa8-402956d2276e	\N	\N	\N
eecf9f69-c635-4a47-b17d-01531afbe3fc	adcaa634-0f41-4135-ad80-d6e22ebb2533	2026-04-15 18:17:55.180448	\N	\N	Secretaria	secretaria	ADMINISTRATIVE	21827ae6-3a67-4316-8aa8-402956d2276e	\N	\N	\N
356dc720-18ca-4e66-a2e2-bfba88b15581	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-18 16:33:34.273684	\N	\N	Ministério De Mídia	ministerio-de-midia	MINISTRY	32c01372-c748-4e05-b348-aa0830a9429b	\N	\N	\N
814ef50c-4ad2-44e7-bb13-77d8a0630aea	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-18 16:34:44.147583	\N	\N	Patrimônio	patrimonio	MINISTRY	32c01372-c748-4e05-b348-aa0830a9429b	\N	\N	\N
840fad56-079e-4f66-b372-d6bb4510c443	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-18 16:34:57.770543	\N	\N	Tesouraria	tesouraria	ADMINISTRATIVE	32c01372-c748-4e05-b348-aa0830a9429b	\N	\N	\N
af114d6c-7622-46b6-bad7-a7bf852a5be7	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-21 00:28:58.938131	\N	\N	Recepção	recepcao	MINISTRY	32c01372-c748-4e05-b348-aa0830a9429b	\N	\N	\N
a80d5151-a1c0-4eae-99e4-6b068ac2c762	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-25 04:04:26.452723	\N	\N	Ministério De Tecnologia	tecnologia	MINISTRY	32c01372-c748-4e05-b348-aa0830a9429b	\N	\N	\N
\.


--
-- Data for Name: extensions_subscriptions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.extensions_subscriptions (id, created_by, created_date, last_modified_by, last_modified_date, department_id, extension) FROM stdin;
80497074-2901-4be7-9d16-56d8cbe3175d	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 01:32:07.775217	\N	\N	729bdee6-db1d-45b8-acd9-344f2ad3ed67	SOMA
90453d37-a0af-4d6b-aa90-3796d33141b1	adcaa634-0f41-4135-ad80-d6e22ebb2533	2026-04-15 18:17:58.582709	\N	\N	eecf9f69-c635-4a47-b17d-01531afbe3fc	SOMA
\.


--
-- Data for Name: inactive_people; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.inactive_people (church_id, email, id) FROM stdin;
588926cc-b889-49f1-a81a-f31fa3243d99	nate@ibb.com	588926cc-b889-49f1-a81a-f31fa3243d99
20c3d107-4b47-40a9-a378-084b0e490201	rita@email.com	eec563b0-1787-44e0-85b6-669313269c5a
\.


--
-- Data for Name: integrations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.integrations (id, created_by, created_date, last_modified_by, last_modified_date, department_id, membership_id, type) FROM stdin;
9b3533f2-e4aa-4a2a-bbbf-6815d731f31c	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 01:32:07.756923	\N	\N	729bdee6-db1d-45b8-acd9-344f2ad3ed67	ddbaa35f-9955-467f-9071-1219d1101bc6	LEADER
7e01f0c9-9bdf-4c6c-aa6d-f2b4a7f9de40	adcaa634-0f41-4135-ad80-d6e22ebb2533	2026-04-15 18:17:57.961378	\N	\N	eecf9f69-c635-4a47-b17d-01531afbe3fc	fb2cd382-4a11-46c5-9cec-7826bab3c545	LEADER
9ec781ce-8156-4102-8b44-5c5580853022	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-01 01:18:13.412852	\N	\N	356dc720-18ca-4e66-a2e2-bfba88b15581	ddbaa35f-9955-467f-9071-1219d1101bc6	INTEGRANT
604ba687-03ad-4a0b-ba85-b550319944e8	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-01 01:18:14.205736	\N	\N	356dc720-18ca-4e66-a2e2-bfba88b15581	3f88b508-5ca8-4dca-900a-bcbfb1fa17f0	INTEGRANT
6917bef7-ea58-46c9-8e25-2f1a2a688a30	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-01 01:18:15.109548	\N	\N	356dc720-18ca-4e66-a2e2-bfba88b15581	6451b91f-478e-4118-a467-91f05ce8a40a	INTEGRANT
d4236810-6115-494c-9b86-b17f1a94f832	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-01 13:55:22.895614	\N	\N	a80d5151-a1c0-4eae-99e4-6b068ac2c762	ddbaa35f-9955-467f-9071-1219d1101bc6	INTEGRANT
95c5a291-faa4-4824-a1b5-7c523dd17519	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-01 13:55:57.271521	\N	\N	840fad56-079e-4f66-b372-d6bb4510c443	6451b91f-478e-4118-a467-91f05ce8a40a	INTEGRANT
1edb2136-651c-4a0d-8a16-d7ad241ba045	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-01 14:00:07.832428	\N	\N	af114d6c-7622-46b6-bad7-a7bf852a5be7	6451b91f-478e-4118-a467-91f05ce8a40a	INTEGRANT
6e6dbf3b-36bf-4611-abe0-dea97f5ef89d	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-01 18:59:14.378638	\N	\N	a80d5151-a1c0-4eae-99e4-6b068ac2c762	1368767a-5be1-4447-93cb-180235384837	INTEGRANT
144dd7f9-22d2-487f-b3ce-2f1f81b2f756	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-01 18:59:26.410417	\N	\N	729bdee6-db1d-45b8-acd9-344f2ad3ed67	1368767a-5be1-4447-93cb-180235384837	INTEGRANT
\.


--
-- Data for Name: links; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.links (id, created_by, created_date, last_modified_by, last_modified_date, label, url) FROM stdin;
c004c5c1-cb7f-46a6-8482-0a44f0d51f58	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-04 18:37:25.024179	\N	\N	Instagram	instagram.com/ibbmossoro/
\.


--
-- Data for Name: media; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.media (id, created_by, created_date, last_modified_by, last_modified_date, file_size, mime_type, original_filename) FROM stdin;
802c9be8-fcd0-4f3e-8050-28704052ab8b	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-04 20:04:00.4655	\N	\N	1304389	image/jpeg	1000256910.jpg
01584bbd-66fd-4fdd-b21b-1f3bf3347210	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-11 15:48:16.897969	\N	\N	1424374	image/png	1000258230.png
c04d2098-b335-4875-9390-1acde716d6be	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-11 15:49:40.823746	\N	\N	1424374	image/png	1000258230.png
8212fdfb-e865-4f21-9b78-30116e6ef9af	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-11 15:55:38.29186	\N	\N	1424374	image/png	1000258230.png
8b5b1710-29b2-49ba-8786-4c646e7e6028	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-11 16:49:12.545238	\N	\N	1424374	image/png	1000258230.png
d126766c-6654-4904-8a8e-0ba608213182	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-11 18:21:03.354595	\N	\N	147263	image/jpeg	1000259224.jpg
88a02fea-4e11-4d9f-a503-05c42622c667	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-11 18:27:31.250537	\N	\N	849140	image/jpeg	1000259269.jpg
6bf898f9-b38a-408f-b120-a4fa943c6b31	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-12 00:56:29.136889	\N	\N	317661	image/jpeg	1000259934.jpg
b56a2700-dc9b-41f0-aa80-9fe5ea9ee1dc	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-12 02:09:26.623727	\N	\N	172108	image/jpeg	1000259394.jpg
5eb6242f-67ad-4622-8089-052ed2152ec2	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-12 03:19:07.695478	\N	\N	130982	image/jpeg	1000258692.jpg
55d7c9f2-5bff-4b49-b941-8632e6169411	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-14 18:21:17.728984	\N	\N	2077554	image/jpeg	4dc86a37-f441-4e14-9ae9-9e51848a0f8c-1_all_40556.jpg
\.


--
-- Data for Name: memberships; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.memberships (id, created_by, created_date, last_modified_by, last_modified_date, affiliation, entry_date, entry_mode, leave_date, leave_mode, leave_note, person_id, unit_id) FROM stdin;
ddbaa35f-9955-467f-9071-1219d1101bc6	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 01:32:07.522481	\N	\N	MEMBER	\N	\N	\N	\N	\N	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	32c01372-c748-4e05-b348-aa0830a9429b
30c035c4-bbe7-43fe-bc29-c1afd0e706e8	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 01:34:17.758181	\N	\N	MEMBER	\N	BAPTISM	\N	\N	\N	588926cc-b889-49f1-a81a-f31fa3243d99	32c01372-c748-4e05-b348-aa0830a9429b
fb2cd382-4a11-46c5-9cec-7826bab3c545	adcaa634-0f41-4135-ad80-d6e22ebb2533	2026-04-15 18:17:53.410212	\N	\N	MEMBER	\N	\N	\N	\N	\N	adcaa634-0f41-4135-ad80-d6e22ebb2533	21827ae6-3a67-4316-8aa8-402956d2276e
6451b91f-478e-4118-a467-91f05ce8a40a	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-21 23:37:04.384078	\N	\N	MEMBER	1998-02-14	BAPTISM	\N	\N	\N	eec563b0-1787-44e0-85b6-669313269c5a	32c01372-c748-4e05-b348-aa0830a9429b
3f88b508-5ca8-4dca-900a-bcbfb1fa17f0	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-27 20:24:58.918164	\N	\N	MEMBER	\N	\N	\N	\N	\N	49b5d9a9-f812-416c-9692-0a6cbcc7cd01	32c01372-c748-4e05-b348-aa0830a9429b
86eb886b-bb8d-4404-9fe8-aa7ae2e5ca2e	adcaa634-0f41-4135-ad80-d6e22ebb2533	2026-04-29 02:27:29.401693	\N	\N	CONGREGATED	\N	\N	\N	\N	\N	49b5d9a9-f812-416c-9692-0a6cbcc7cd01	21827ae6-3a67-4316-8aa8-402956d2276e
1368767a-5be1-4447-93cb-180235384837	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-01 18:54:01.785685	\N	\N	MEMBER	\N	\N	\N	\N	\N	5afb5c93-95e6-4ddc-948a-9b12043c589d	32c01372-c748-4e05-b348-aa0830a9429b
\.


--
-- Data for Name: openfga_migrations_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.openfga_migrations_logs (id, description, details, executed_at, status, version) FROM stdin;
\.


--
-- Data for Name: pending_integrations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pending_integrations (id, department_id, membership_id) FROM stdin;
\.


--
-- Data for Name: pending_memberships; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pending_memberships (id, created_by, created_date, last_modified_by, last_modified_date, affiliation, entry_date, entry_mode, person_id, unit_confirmation_date, unit_id, user_confirmation_date) FROM stdin;
\.


--
-- Data for Name: people; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.people (id, created_by, created_date, last_modified_by, last_modified_date, address_id, birth_date, full_name, gender, nickname, phone, profile_image_id) FROM stdin;
adcaa634-0f41-4135-ad80-d6e22ebb2533	adcaa634-0f41-4135-ad80-d6e22ebb2533	2026-04-15 18:15:11.597748	\N	\N	\N	1968-01-01	Maria Edileuza Medley	FEMALE	\N	\N	\N
588926cc-b889-49f1-a81a-f31fa3243d99	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 01:34:17.703978	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 19:12:44.566585	0de09b99-75c3-404a-a77f-bf5db33492ea	2008-01-01	Nathalia Emily da Silva	FEMALE	Nat	84 98765-4321	\N
eec563b0-1787-44e0-85b6-669313269c5a	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-21 23:37:04.322002	\N	\N	\N	1981-03-16	Rita Celcina Da Silva	FEMALE	Ritinha	84988648709	\N
5afb5c93-95e6-4ddc-948a-9b12043c589d	5afb5c93-95e6-4ddc-948a-9b12043c589d	2026-05-01 18:49:09.965586	\N	\N	\N	2000-01-27	Fernando Lucas da Silva	MALE	\N	\N	\N
49b5d9a9-f812-416c-9692-0a6cbcc7cd01	49b5d9a9-f812-416c-9692-0a6cbcc7cd01	2026-04-27 17:29:15.73528	49b5d9a9-f812-416c-9692-0a6cbcc7cd01	2026-05-14 14:54:39.565582	\N	2008-01-03	Nathalia	FEMALE	Nat	\N	\N
7e23cc33-ba03-4302-9ab9-2b1469a2dc68	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 01:27:31.204477	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-14 18:21:18.556206	\N	2001-08-01	Lisandra Cristina da Silva	FEMALE	Lili	8455512864	55d7c9f2-5bff-4b49-b941-8632e6169411
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name, slug) FROM stdin;
\.


--
-- Data for Name: unit_calendar_events; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.unit_calendar_events (unit_id, id) FROM stdin;
32c01372-c748-4e05-b348-aa0830a9429b	664eeb5e-12e4-4165-8490-578a7accfdfa
\.


--
-- Data for Name: unit_links; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.unit_links (link_id, unit_id) FROM stdin;
c004c5c1-cb7f-46a6-8482-0a44f0d51f58	32c01372-c748-4e05-b348-aa0830a9429b
\.


--
-- Data for Name: units; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.units (id, created_by, created_date, last_modified_by, last_modified_date, address_id, church_id, email, name, phone, slug, type, cover_image_id, profile_image_id) FROM stdin;
21827ae6-3a67-4316-8aa8-402956d2276e	adcaa634-0f41-4135-ad80-d6e22ebb2533	2026-04-15 18:17:52.456383	\N	\N	07ddc527-18d0-4ee7-9a6d-6efa527c15ec	a75ffd55-a9f6-46ed-88d9-12a0f0f9a504					MAIN	\N	\N
32c01372-c748-4e05-b348-aa0830a9429b	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-04-15 01:32:07.452915	7e23cc33-ba03-4302-9ab9-2b1469a2dc68	2026-05-12 00:56:29.703148	08c6845c-2859-4b86-bae3-ede98ea1bfb0	20c3d107-4b47-40a9-a378-084b0e490201	ibbmossoro@email.com	Igreja Batista Betel	(84) 96333-2223	ibbmossoro	MAIN	802c9be8-fcd0-4f3e-8050-28704052ab8b	6bf898f9-b38a-408f-b120-a4fa943c6b31
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (email, email_verified_at, password, username, id) FROM stdin;
lisandra.crisilva37@gmail.com	\N	$2a$10$I/UESlqkm1PlajvnVp2VYu8ovh89sRbzU5K5Sus.7kKdph.aHITpa	lis	7e23cc33-ba03-4302-9ab9-2b1469a2dc68
ed@email.com	\N	$2a$10$NAQuuOsMdO0Ojr/aj3.6W.F4NqV3bg2Ya31GQgz/eF56lTdWN.oLS	edileuza	adcaa634-0f41-4135-ad80-d6e22ebb2533
nat@email.com	\N	$2a$10$xeoYyP6btY8kEFasz.4klek9e.fUJnFMdTdPfljtANlh0IbopNyeq	nat	49b5d9a9-f812-416c-9692-0a6cbcc7cd01
fernandophes@gmail.com	\N	$2a$10$DJzqB2q4Dsoq6g8JAXIGIu0qYBtkWzqP8wUoEhCyLs95oC2UmWakO	fernandophes	5afb5c93-95e6-4ddc-948a-9b12043c589d
\.


--
-- Name: abilities abilities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.abilities
    ADD CONSTRAINT abilities_pkey PRIMARY KEY (id);


--
-- Name: addresses addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses
    ADD CONSTRAINT addresses_pkey PRIMARY KEY (id);


--
-- Name: calendar_event calendar_event_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.calendar_event
    ADD CONSTRAINT calendar_event_pkey PRIMARY KEY (id);


--
-- Name: churches churches_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.churches
    ADD CONSTRAINT churches_pkey PRIMARY KEY (id);


--
-- Name: department_calendar_events department_calendar_events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department_calendar_events
    ADD CONSTRAINT department_calendar_events_pkey PRIMARY KEY (id);


--
-- Name: departments departments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT departments_pkey PRIMARY KEY (id);


--
-- Name: extensions_subscriptions extensions_subscriptions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extensions_subscriptions
    ADD CONSTRAINT extensions_subscriptions_pkey PRIMARY KEY (id);


--
-- Name: inactive_people inactive_people_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inactive_people
    ADD CONSTRAINT inactive_people_pkey PRIMARY KEY (id);


--
-- Name: integrations integrations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.integrations
    ADD CONSTRAINT integrations_pkey PRIMARY KEY (id);


--
-- Name: links links_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.links
    ADD CONSTRAINT links_pkey PRIMARY KEY (id);


--
-- Name: media media_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.media
    ADD CONSTRAINT media_pkey PRIMARY KEY (id);


--
-- Name: memberships memberships_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.memberships
    ADD CONSTRAINT memberships_pkey PRIMARY KEY (id);


--
-- Name: openfga_migrations_logs openfga_migrations_logs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.openfga_migrations_logs
    ADD CONSTRAINT openfga_migrations_logs_pkey PRIMARY KEY (id);


--
-- Name: pending_integrations pending_integrations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pending_integrations
    ADD CONSTRAINT pending_integrations_pkey PRIMARY KEY (id);


--
-- Name: pending_memberships pending_memberships_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pending_memberships
    ADD CONSTRAINT pending_memberships_pkey PRIMARY KEY (id);


--
-- Name: people people_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.people
    ADD CONSTRAINT people_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: churches uk6iapv7k0de579ltcavutl8nxp; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.churches
    ADD CONSTRAINT uk6iapv7k0de579ltcavutl8nxp UNIQUE (email);


--
-- Name: abilities ukc3rhk7bx3ff51m8we43jveeac; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.abilities
    ADD CONSTRAINT ukc3rhk7bx3ff51m8we43jveeac UNIQUE (person_id, role_id);


--
-- Name: departments ukc4ghs2377nrvxj86493d8jifg; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT ukc4ghs2377nrvxj86493d8jifg UNIQUE (unit_id, slug);


--
-- Name: abilities ukcad1nl0jqhaeaesfo237lqa6p; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.abilities
    ADD CONSTRAINT ukcad1nl0jqhaeaesfo237lqa6p UNIQUE (user_id, role_id);


--
-- Name: churches ukgmut9ymxemf237ovo9o4ttsm2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.churches
    ADD CONSTRAINT ukgmut9ymxemf237ovo9o4ttsm2 UNIQUE (slug);


--
-- Name: pending_memberships ukita0ir1cno9yg0u2mcdm2j4r9; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pending_memberships
    ADD CONSTRAINT ukita0ir1cno9yg0u2mcdm2j4r9 UNIQUE (unit_id, person_id);


--
-- Name: departments ukjo62m92rkrnuhifsrram3u0un; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT ukjo62m92rkrnuhifsrram3u0un UNIQUE (unit_id, name);


--
-- Name: integrations ukl016resf2nr073hl313o4nwe9; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.integrations
    ADD CONSTRAINT ukl016resf2nr073hl313o4nwe9 UNIQUE (department_id, membership_id);


--
-- Name: openfga_migrations_logs uklofmfwj75lana6mugrcpekf0f; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.openfga_migrations_logs
    ADD CONSTRAINT uklofmfwj75lana6mugrcpekf0f UNIQUE (version);


--
-- Name: units uknc3rwq8yhp2ulfh9q5xnnk6pm; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.units
    ADD CONSTRAINT uknc3rwq8yhp2ulfh9q5xnnk6pm UNIQUE (church_id, name);


--
-- Name: roles ukofx66keruapi6vyqpv6f2or37; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT ukofx66keruapi6vyqpv6f2or37 UNIQUE (name);


--
-- Name: units ukpvidpkbokvdqdfruhaax7sns6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.units
    ADD CONSTRAINT ukpvidpkbokvdqdfruhaax7sns6 UNIQUE (church_id, slug);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: pending_integrations ukrc2kigkrexupqvseyj2igjtux; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pending_integrations
    ADD CONSTRAINT ukrc2kigkrexupqvseyj2igjtux UNIQUE (department_id, membership_id);


--
-- Name: extensions_subscriptions uks64xg676tabmh9t7t9hvtvwt1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extensions_subscriptions
    ADD CONSTRAINT uks64xg676tabmh9t7t9hvtvwt1 UNIQUE (department_id, extension);


--
-- Name: roles uksx80rwev5en94r3jv7riyoh1y; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT uksx80rwev5en94r3jv7riyoh1y UNIQUE (slug);


--
-- Name: unit_calendar_events unit_calendar_events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unit_calendar_events
    ADD CONSTRAINT unit_calendar_events_pkey PRIMARY KEY (id);


--
-- Name: unit_links unit_links_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unit_links
    ADD CONSTRAINT unit_links_pkey PRIMARY KEY (link_id, unit_id);


--
-- Name: units units_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.units
    ADD CONSTRAINT units_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: inactive_people fk87iascu4atkqubn3hbbxlvt2g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inactive_people
    ADD CONSTRAINT fk87iascu4atkqubn3hbbxlvt2g FOREIGN KEY (id) REFERENCES public.people(id);


--
-- Name: unit_calendar_events fkc1rihx21pohy9by2f1vf62jmp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unit_calendar_events
    ADD CONSTRAINT fkc1rihx21pohy9by2f1vf62jmp FOREIGN KEY (id) REFERENCES public.calendar_event(id);


--
-- Name: users fkk65bcoc4wjhie6lvbt1446k1f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkk65bcoc4wjhie6lvbt1446k1f FOREIGN KEY (id) REFERENCES public.people(id);


--
-- Name: department_calendar_events fknm2b4tl3xrdie5n6xjrw3v644; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department_calendar_events
    ADD CONSTRAINT fknm2b4tl3xrdie5n6xjrw3v644 FOREIGN KEY (id) REFERENCES public.calendar_event(id);


--
-- PostgreSQL database dump complete
--

\unrestrict zx9mxUIu4KOXCOBqqeJaA3wO1GEWtX0SXLXxhcuv1879PdlPj5HoBC2ABZX66Fg

