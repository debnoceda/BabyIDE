.data
newline: .asciiz "\n"
.text
main:

.data
x: .word 1
.text

lw $t0, x


.data
y: .word 2
.text

lw $t1, y


.data
ans: .word 0
.text

lw $t1, ans


li $v0, 1
lw $a0, ans
syscall


li $v0, 4
la $a0, newline
syscall


li $v0, 10
syscall

