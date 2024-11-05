package kakao.com.tess.user.controller

import kakao.com.tess.user.entity.User
import kakao.com.tess.user.repository.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepository: UserRepository
) {

    // 사용자 생성
    @PostMapping
    fun createUser(@RequestBody user: User): User {
        return userRepository.save(user)
    }

    // 사용자 조회
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }
}
