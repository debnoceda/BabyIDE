.data
newline: .asciiz "\n"
.text
main:
    li $t0 1

.data
x: .word 1
.text

    lw $t1, x

    li $t2 2

.data
y: .word 2
.text

    lw $t3, y

    add $t4 $t1 $t3

.data
ans: .word 0
.text

    sw $t4, ans


    li $v0, 1
    lw $a0, ans
    syscall


    li $v0, 4
    la $a0, newline
    syscall


    li $v0, 10
    syscall

