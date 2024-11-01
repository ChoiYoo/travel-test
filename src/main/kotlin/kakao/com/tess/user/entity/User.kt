package kakao.com.tess.user.entity

import jakarta.persistence.*
import kakao.com.tess.user.model.UserRole

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // might can change oauth userid
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    var userName: String,

    var profileUrl: String,

    var userRole: UserRole = UserRole.HOST,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    var groupRoles: MutableSet<UserGroupRole> = mutableSetOf(),
    )