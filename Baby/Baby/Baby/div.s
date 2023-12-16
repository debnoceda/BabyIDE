.data
newline: .asciiz "\n"
.text
main:
    li $t0 9

.data
x: .word 9
.text

    lw $t1, x

    li $t2 3

.data
y: .word 3
.text

    lw $t3, y

    div $t1 $t3
    mflo $t4

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

