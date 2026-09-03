create table if not exists themes (
    id bigserial primary key,
    title text
);

create table if not exists folders (
    id bigserial primary key,
    title text,
    theme_id bigint,
    constraint fk_folder_theme foreign key (theme_id) references themes(id)
);

create table if not exists cards (
    id bigserial primary key,
    title text,
    description text,
    code text,
    folder_id bigint,
    constraint fk_card_folder foreign key (folder_id) references folders(id)
);

