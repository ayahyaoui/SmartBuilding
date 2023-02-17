#include <stdio.h> 
#include <stdlib.h> 
#include "ilp.h" 

/* Global variables */ 


ILP_Object ilp_program () 
{ 
{ 
  ILP_Object ilptmp89; 
ilptmp89 = ILP_Integer2ILP(22); 

  {
    ILP_Object i1 = ilptmp89;
{ 
  ILP_Object ilptmp90; 
ilptmp90 = ILP_Float2ILP(6.3); 

  {
    ILP_Object f2 = ilptmp90;
{ 
  ILP_Object ilptmp91; 
  ILP_Object ilptmp92; 
ilptmp91 = f2; 
ilptmp92 = i1; 
return ILP_Plus(ilptmp91, ilptmp92);
} 

  }
}

  }
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
