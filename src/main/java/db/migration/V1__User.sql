create table User (
   username varchar(12) not null,
   password varchar(255) not null,
    --salt varchar(255) not null,
    primary key (username)
);

create table Game (
    gameName varchar(100) not null,
    primary key (gameName)
);

create table UserGame (
    username varchar(12) not null,
    gameName varchar(100) not null,
    hand varchar(100) not null,
    constraint username_fk foreign key (username) references User(username),
    constraint gameName_fk foreign key (gameName) references Game(gameName)
    primary key (username, gameName)
);

