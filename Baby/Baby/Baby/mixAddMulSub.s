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


.data
a: .word 11
.text

    lw $t3, a

    mul $t4 $t1 $t2

    add $t5 $t0 $t4

    sub $t6 $t5 $t3


.data
ans: .word 0
.text

    sw $t6, ans


    li $v0, 1
    lw $a0, ans
    syscall


    li $v0, 4
    la $a0, newline
    syscall


    li $v0, 10
    syscall

