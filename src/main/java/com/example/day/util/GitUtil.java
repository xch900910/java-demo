package com.example.day.util;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-08-31 16:55
 **/

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.CleanCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RmCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.HttpConfig;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

@Slf4j
@Component
public class GitUtil {
    @Value("${git.remoteRepo}")
    private String remotePath;
    @Value("${git.localPath}")
    private String localPath;
    @Value("${git.username}")
    private String username;
    @Value("${git.password}")
    private String password;


    private Git getGit() throws Exception {
        Git git = null;
        File file = new File(localPath);
        String path = file.getAbsolutePath().replaceAll("\\\\", "/");
        if (new File(path).exists()) {
            git = Git.open(new File(path));
        } else {
            git = Git.cloneRepository().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).setURI(remotePath)
                    .setDirectory(new File(path)).call();
        }
        //设置一下post内存，否则可能会报错Error writing request body to server
        git.getRepository().getConfig().setInt(HttpConfig.HTTP, null, HttpConfig.POST_BUFFER_KEY, 512 * 1024 * 1024);
        return git;
    }

    public CredentialsProvider getCredentialsProvider() {
        return new UsernamePasswordCredentialsProvider(username, password);
    }

    public Repository getRepository() throws Exception {
        return getGit().getRepository();
    }

    public void pull() throws Exception {
        Git git = getGit();
        git.pull().setRemoteBranchName("master").setCredentialsProvider(getCredentialsProvider()).call();
        log.info("拉取完毕");
        git.getRepository().close();
    }

    public void cleanAndPull() throws Exception {
        Git git = getGit();
        CleanCommand clean = git.clean();
        clean.setCleanDirectories(true);
        clean.call();
        git.pull().setRemoteBranchName("master").setCredentialsProvider(getCredentialsProvider()).call();
        log.info("清理本地目录，重新拉取完毕");
        git.getRepository().close();
    }

    public void pushAdd(String filepattern, String message)
            throws Exception {
        Git git = getGit();
        git.add().addFilepattern(filepattern).call();
        git.add().setUpdate(true);

        git.commit().setMessage(message).call();
        git.push().setCredentialsProvider(getCredentialsProvider()).call();
        git.getRepository().close();

    }

    public void push(String message, String filepattern, Integer workspaceId, String iterationName, String groupName) {

        try {
            Git git = getGit();
            RmCommand rm = git.rm();
            Set<String> deletedFiles = git.status().call().getMissing();

            if (!deletedFiles.isEmpty()) {
                RmCommand rmCommand = git.rm();
                deletedFiles.forEach(rmCommand::addFilepattern);
                rmCommand.call();
            }
            git.add().addFilepattern(filepattern).call();
            git.add().setUpdate(true);
            RevCommit revCommit = git.commit().setMessage(message).call();

            log.info("提交id：{}", revCommit.getName());
            Iterable<PushResult> call = git.push().setCredentialsProvider(getCredentialsProvider()).call();
            Iterator<PushResult> iterator = call.iterator();
            if (iterator.hasNext()) {
                PushResult next = iterator.next();
                log.info("推送返回 pushResult：{},message:{}", JSONObject.toJSONString(next.getTrackingRefUpdates()), JSONObject.toJSONString(next.getMessages()));
            }
            git.getRepository().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 本地新建仓库
     */
    public void createRepo(String path) throws IOException {
        //本地新建仓库地址
        Repository newRepo = FileRepositoryBuilder.create(new File(path + "/.git"));
        newRepo.create();
    }


}
