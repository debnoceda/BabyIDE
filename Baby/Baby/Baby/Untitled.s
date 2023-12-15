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
x: .asciiz "heeey"
.text


    li $v0, 4
    la $a0, x
    syscall


    li $v0, 4
    la $a0, newline
    syscall

