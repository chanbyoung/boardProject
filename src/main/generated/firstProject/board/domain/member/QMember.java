package firstProject.board.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1635026353L;

    public static final QMember member = new QMember("member1");

    public final firstProject.board.domain.QBaseEntity _super = new firstProject.board.domain.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath birth = createString("birth");

    public final ListPath<firstProject.board.domain.post.Comment, firstProject.board.domain.post.QComment> comments = this.<firstProject.board.domain.post.Comment, firstProject.board.domain.post.QComment>createList("comments", firstProject.board.domain.post.Comment.class, firstProject.board.domain.post.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath loginId = createString("loginId");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final ListPath<firstProject.board.domain.post.Post, firstProject.board.domain.post.QPost> posts = this.<firstProject.board.domain.post.Post, firstProject.board.domain.post.QPost>createList("posts", firstProject.board.domain.post.Post.class, firstProject.board.domain.post.QPost.class, PathInits.DIRECT2);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

