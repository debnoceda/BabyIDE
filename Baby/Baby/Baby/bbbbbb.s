.data
newline: .asciiz "\n"
.text
main:
    li $t0 2

.data
x: .word 2
.text

    lw $t1, x

    mul $t2 $t1 $t1

.data
ans: .word 0
.text

    sw $t2, ans


    li $v0, 1
    lw $a0, ans
    syscall


    li $v0, 4
    la $a0, newline
    syscall

