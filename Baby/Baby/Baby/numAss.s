.data
newline: .asciiz "\n"
.text
main:
    li $t0 1
    li $t1 7
    li $t2 3
    mul $t3 $t1 $t2
    add $t2 $t0 $t3

.data
x: .word 0
.text

    sw $t2, x

    li $t0 2
    div $t2 $t0
    mflo $t4
    li $t0 9
    add $t5 $t4 $t0
    li $t0 5
    sub $t6 $t5 $t0

.data
y: .word 0
.text

    sw $t6, y


.data
z: .asciiz "The answer is: "
.text


    li $v0, 4
    la $a0, z
    syscall


    li $v0, 1
    lw $a0, y
    syscall


    li $v0, 4
    la $a0, newline
    syscall


    li $v0, 10
    syscall

