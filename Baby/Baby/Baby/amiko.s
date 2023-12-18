.data
newline: .asciiz "\n"
.text
main:
    li $t0 3
    li $t1 3
    add $t2 $t0 $t1
    li $t1 9
    sub $t3 $t2 $t1
    li $t1 4
    add $t4 $t3 $t1
    li $t1 6
    li $t5 4
    mul $t6 $t1 $t5
    li $t5 5
    mul $t7 $t6 $t5
    add $t5 $t4 $t7

.data
x: .word 0
.text

    sw $t5, x


    li $v0, 1
    lw $a0, x
    syscall


    li $v0, 4
    la $a0, newline
    syscall


.data
statement_1: .asciiz "Wasumama? "
.text
    li $v0, 4
    la $a0, statement_1
    syscall


.data
  y: .word 0
.text

    li $v0, 5
    syscall
    sw $v0 y
    lw $t8, y

    add $t9 $t5 $t8

.data
z: .word 0
.text

    sw $t9, z


    li $v0, 1
    lw $a0, z
    syscall


    li $v0, 4
    la $a0, newline
    syscall


    li $v0, 10
    syscall

