.data
newline: .asciiz "\n"
.text
main:
    li $t0 1

.data
x: .word 1
.text

    lw $t1, x


    li $v0, 1
    lw $a0, x
    syscall


    li $v0, 4
    la $a0, newline
    syscall


    li $v0, 10
    syscall

