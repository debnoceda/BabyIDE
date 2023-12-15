.data
newline: .asciiz "\n"
.text
main:

.data
  x: .space 256
.text

    li $v0, 8
    la $a0 x
    li $a1, 255
    syscall


.data
  y: .space 256
.text

    li $v0, 8
    la $a0 y
    li $a1, 255
    syscall


.data
statement_10: .asciiz "The number is: "
.text
    li $v0, 4
    la $a0, statement_10
    syscall


    li $v0, 4
    la $a0, x
    syscall


    li $v0, 4
    la $a0, newline
    syscall


    li $v0, 10
    syscall

