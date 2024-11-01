package kakao.com.tess.user.entity

import jakarta.persistence.*
import kakao.com.tess.group.entity.Group
import kakao.com.tess.user.model.UserRole

@Entity
class UserGroupRole(
//    userId: xxx가 groupId: xxx에서의 role이 무엇인지 -> 다른 그룹에서는 host, 다른 그룹에서는 participant일 수 있다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    val group: Group,

    @Enumerated(EnumType.STRING)
    var role: UserRole,
)