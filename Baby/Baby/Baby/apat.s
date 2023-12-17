.data
newline: .asciiz "\n"
.text
main:

.data
statement_0: .asciiz "Enter x: "
.text
    li $v0, 4
    la $a0, statement_0
    syscall


.data
  x: .word 0
.text

    li $v0, 5
    syscall
    sw $v0 x
    lw $t0, x

    li $t1 10

.data
a: .word 10
.text

    lw $t2, a


.data
statement_1: .asciiz "Enter x: "
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
    lw $t3, y

    li $t4 100

.data
z: .word 100
.text

    lw $t5, z


.data
ans: .word 0
.text

    move $t6, $t0
    sw $t6, ans


.data
statement_2: .asciiz "The answer is: "
.text
    li $v0, 4
    la $a0, statement_2
    syscall


    li $v0, 1
    lw $a0, ans
    syscall


    li $v0, 4
    la $a0, newline
    syscall


    li $v0, 10
    syscall

