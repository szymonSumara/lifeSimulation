package agh.cs.lab;

import java.util.Random;

public class GeneticCode {
    private final int  geneticCodeSize = 32;
    private final int[] geneticCode = new int[this.geneticCodeSize];
    private final int numberOfPossibleGenes = 8;
    private final Random random = new Random();

    public GeneticCode(){
        for(int i = 0;i<this.geneticCodeSize;i++)
            this.geneticCode[i] = random.nextInt(numberOfPossibleGenes);

        if(!isCorrect())
            this.repairGenCode();
    }

    public GeneticCode(GeneticCode strongerAnimalGeneticCode,GeneticCode weakerAnimalGeneticCode){
        int firstGenCut = new Random().nextInt(this.geneticCodeSize-2);
        int secondGenCut = new Random().nextInt(this.geneticCodeSize - firstGenCut -1);
        secondGenCut+=firstGenCut + 1;

        for(int i=0;i<firstGenCut;i++)
            this.geneticCode[i] =  strongerAnimalGeneticCode.getGeneAt(i);

        for(int i=firstGenCut;i<secondGenCut;i++)
            this.geneticCode[i] =  weakerAnimalGeneticCode.getGeneAt(i);

        for(int i=secondGenCut;i<this.geneticCodeSize;i++)
            this.geneticCode[i] =  strongerAnimalGeneticCode.getGeneAt(i);

        if(!isCorrect())
            this.repairGenCode();
    }

    public int getRandomGene(){
        return this.geneticCode[random.nextInt(this.geneticCodeSize)];
    }

    public int getGeneAt(int positionGene){
        return this.geneticCode[positionGene];
    }

    private boolean isCorrect(){
        int[] genesCount = new int[numberOfPossibleGenes];

        for(int gene : this.geneticCode)
            genesCount[gene]+=1;

        for(int geneCount : this.geneticCode)
            if(geneCount == 0)
                return false;

        return true;
    }

    private void repairGenCode(){
        int[] genesCount = new int[this.numberOfPossibleGenes];

        for(int gene : this.geneticCode)
            genesCount[gene]+=1;

        for(int i=0;i<8;i++){
            while(genesCount[i] == 0){
                int index = random.nextInt(this.geneticCodeSize);
                if(genesCount[this.geneticCode[index]] > 1){
                    genesCount[this.geneticCode[index]] -= 1;
                    genesCount[i]+=1;
                }
            }
        }
    }


    @Override
    public String toString(){
        StringBuilder geneStringRepresentation = new StringBuilder();

        for(int gene: this.geneticCode)
            geneStringRepresentation.append(gene);

        return geneStringRepresentation.toString();
    }

}
