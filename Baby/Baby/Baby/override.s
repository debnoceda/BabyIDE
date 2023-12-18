.data
newline: .asciiz "\n"
.text
main:
    li $t0 2

.data
x: .word 2
.text

    lw $t1, x

    li $t2 3
    li $t1, 3
    move $t2, $t1
    sw $t2, x


    li $v0, 1
    lw $a0, x
    syscall


    li $v0, 4
    la $a0, newline
    syscall


    li $v0, 10
    syscall

