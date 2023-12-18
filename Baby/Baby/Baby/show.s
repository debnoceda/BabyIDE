.data
newline: .asciiz "\n"
.text
main:
    li $t0 3

    li $v0, 1
    move $a0, $t0
    syscall


    li $v0, 10
    syscall

