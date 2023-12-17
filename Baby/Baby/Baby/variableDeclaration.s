.data
newline: .asciiz "\n"
.text
main:
    li $t0 1

.data
x: .word 1
.text

    lw $t1, x


.data
y: .asciiz "I love CMSC 124"
.text


.data
statement_0: .asciiz "Enter z: "
.text
    li $v0, 4
    la $a0, statement_0
    syscall


.data
  z: .word 0
.text

    li $v0, 5
    syscall
    sw $v0 z
    lw $t2, z


.data
statement_1: .asciiz "Enter a: "
.text
    li $v0, 4
    la $a0, statement_1
    syscall


.data
  a: .space 256
.text

    li $v0, 8
    la $a0 a
    li $a1, 255
    syscall


.data
statement_2: .asciiz "The value of x is "
.text
    li $v0, 4
    la $a0, statement_2
    syscall


    li $v0, 1
    lw $a0, x
    syscall


    li $v0, 4
    la $a0, newline
    syscall


.data
statement_3: .asciiz "The value of y is "
.text
    li $v0, 4
    la $a0, statement_3
    syscall


    li $v0, 4
    la $a0, y
    syscall


    li $v0, 4
    la $a0, newline
    syscall


.data
statement_4: .asciiz "The value of z is "
.text
    li $v0, 4
    la $a0, statement_4
    syscall


    li $v0, 1
    lw $a0, z
    syscall


    li $v0, 4
    la $a0, newline
    syscall


.data
statement_5: .asciiz "The value of a is "
.text
    li $v0, 4
    la $a0, statement_5
    syscall


    li $v0, 4
    la $a0, a
    syscall


    li $v0, 4
    la $a0, newline
    syscall


    li $v0, 10
    syscall

