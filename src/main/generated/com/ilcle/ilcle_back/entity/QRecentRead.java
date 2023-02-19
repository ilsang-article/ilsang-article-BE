package com.ilcle.ilcle_back.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecentRead is a Querydsl query type for RecentRead
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecentRead extends EntityPathBase<RecentRead> {

    private static final long serialVersionUID = 481528445L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecentRead recentRead = new QRecentRead("recentRead");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final QPost post;

    public final BooleanPath readCheck = createBoolean("readCheck");

    public QRecentRead(String variable) {
        this(RecentRead.class, forVariable(variable), INITS);
    }

    public QRecentRead(Path<? extends RecentRead> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecentRead(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecentRead(PathMetadata metadata, PathInits inits) {
        this(RecentRead.class, metadata, inits);
    }

    public QRecentRead(Class<? extends RecentRead> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post")) : null;
    }

}

