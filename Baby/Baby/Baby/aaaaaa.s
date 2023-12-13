.data
newline: .asciiz "\n"
.text
main:
    li $t0 1

    li $v0, 1
    lw $a0, x
    syscall


    li $v0, 10
    syscall

