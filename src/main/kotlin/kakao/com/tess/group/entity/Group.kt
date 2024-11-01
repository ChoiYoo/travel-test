package kakao.com.tess.group.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import kakao.com.tess.user.entity.UserGroupRole

@Entity
class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String,

    @OneToMany(mappedBy = "group", cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    var groupRoles: MutableSet<UserGroupRole> = mutableSetOf(),
)