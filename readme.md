# Improved reviewer assignment based on both word and semantic features

## Usage

First download other data, then modify the path ('输出文件目录') in +g.py at line 2156, and finally run +g.py.

## Other data

- Link: https://pan.baidu.com/s/1rdd0c4Cwku9Y5hTO9P4MLw
- Password: b4j4

## If you use your own dataset

1. Determining the initial dataset: a+文档集 (manuscripts), a+作者编号\_论文内容 (reviewers), a+论文号\_作者号广义表.txt、a+作者号\_论文号广义表.txt, 文档-标准作者排名.txt (groundtruth.txt in a+文档集)
2. Run LDAGibbsSampling-master to calculate "a+作者编号\_论文内容", rename the resulting 5 files and put them in the corresponding folders:
   - 词号\_词次数\_词表.txt → 3-词号\_词次数\_词表.txt
   - 文档编号\_文档名表.txt → 3-文档编号\_文档名表.txt
   - phi后缀文件 → 3-主题\_词分布.txt
   - theta后缀文件 → 3-作者\_主题分布.txt
   - tassign后缀文件 → 3-tassign文件.txt
3. Modify the corresponding parameter path and run: 4\_12.py, to get 4 files
4. Modify the corresponding parameter path and run: +e.py, to get 2 files
   - Documentation required: 论文\_方向\_年份\_作者\_引用论文\_被引论文广义表 (Refer to the other data)
     - Format: {paperID:[{Field:None,..},{Year:None,..},{Author:None,..},{citationPaper:None,..},{citationAuthor:None,..},{citedPaper:None,..},{citedPaper:None,..}]}
5. Modify the corresponding parameter path and run: +f.py, to get 2 files
6. Modify the corresponding parameter path and run: +g.py, to get results

## Tips
- The other data provides all the intermediate files for the data in this paper, so you can perform the last step directly.
- If you need the code in English, you can use [tool](https://github.com/aitsc/code-zh-to-en) to translate it.

## Run LDAGibbsSampling-master

1. Install jdk1.8 and eclipse
2. eclipse → File → Open Projects from File System.. → Select the path of LDAGibbsSampling-master to open
3. Modify the corresponding path in LDAGibbsSampling-master/src/liuyang/nlp/lda/conf/PathConfig.java, as well as the parameters in LdaParameters.txt
4. Run LDAGibbsSampling-master/src/liuyang/nlp/lda/main/LdaGibbsSampling.java

# Citation
If you find this code useful, please cite the following paper:
```
@article{tan2021improved,
  title = {Improved reviewer assignment based on both word and semantic features},
  author = {Tan, Shicheng and Duan, Zhen and Zhao, Shu and Chen, Jie and Zhang, Yanping},
  journal = {Information Retrieval Journal},
  year = {2021},
  type = {Journal Article}
}
```