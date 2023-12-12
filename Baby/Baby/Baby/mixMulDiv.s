.data
newline: .asciiz "\n"
.text
main:

.data
x: .word 9
.text

    lw $t0, x


.data
y: .word 3
.text

    lw $t1, y


.data
z: .word 10
.text

    lw $t2, z

    div $t0 $t1
    mflo $t3

    mul $t4 $t3 $t2


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

