#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 
ILP_Object print;


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp151; 
{ 
  ILP_Object ilptmp152; 
{ 
  ILP_Object ilptmp153; 
  ILP_Object ilptmp154; 
ilptmp153 = ILP_Integer2ILP(1); 
ilptmp154 = ILP_Integer2ILP(0); 
ilptmp152 = ILP_GreaterThan(ilptmp153, ilptmp154);
} 
ilptmp151 = ILP_print(ilptmp152);
}
{ 
  ILP_Object ilptmp155; 
{ 
  ILP_Object ilptmp156; 
  ILP_Object ilptmp157; 
ilptmp156 = ILP_Integer2ILP(1); 
ilptmp157 = ILP_Float2ILP(0); 
ilptmp155 = ILP_GreaterThan(ilptmp156, ilptmp157);
} 
ilptmp151 = ILP_print(ilptmp155);
}
{ 
  ILP_Object ilptmp158; 
{ 
  ILP_Object ilptmp159; 
  ILP_Object ilptmp160; 
ilptmp159 = ILP_Float2ILP(1); 
ilptmp160 = ILP_Integer2ILP(0); 
ilptmp158 = ILP_GreaterThan(ilptmp159, ilptmp160);
} 
ilptmp151 = ILP_print(ilptmp158);
}
return ilptmp151; 
} 

} 

static ILP_Object ilp_caught_program () {
  struct ILP_catcher* current_catcher = ILP_current_catcher;
  struct ILP_catcher new_catcher;

  if ( 0 == setjmp(new_catcher._jmp_buf) ) {
    ILP_establish_catcher(&new_catcher);
    return ilp_program();
  };
  return ILP_current_exception;
}

int main (int argc, char *argv[]) 
{ 
  ILP_START_GC; 
  ILP_print(ilp_caught_program()); 
  ILP_newline(); 
  return EXIT_SUCCESS; 
} 
